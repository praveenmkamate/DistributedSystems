package com.ds.avengers.service;

import com.ds.avengers.Constants;
import com.ds.avengers.dto.CustomerOrder;
import com.ds.avengers.dto.DeliveryAgent;
import com.ds.avengers.dto.DeliveryAgentOrders;
import com.ds.avengers.repo.ICustomerOrderRepo;
import com.ds.avengers.repo.IDeliveryAgentOrdersRepo;
import com.ds.avengers.repo.IDeliveryAgentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private IDeliveryAgentRepo deliveryAgentRepo;

    @Autowired
    private ICustomerOrderRepo customerOrderRepo;

    @Autowired
    private IDeliveryAgentOrdersRepo deliveryAgentOrdersRepo;



    @Autowired
    DeliveryService(IDeliveryAgentRepo deliveryAgentRepo, ICustomerOrderRepo customerOrderRepo) {
        this.deliveryAgentRepo = deliveryAgentRepo;
        this.customerOrderRepo = customerOrderRepo;
    }

    public ResponseEntity addDeliveryAgent(DeliveryAgent deliveryAgent) {
        DeliveryAgent queryDeliveryAgent = deliveryAgentRepo.findByDeliveryAgentName(deliveryAgent.getName());
        if (null == queryDeliveryAgent) {
            deliveryAgentRepo.save(deliveryAgent);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity getAvailableDeliveryAgents() {
        return new ResponseEntity<>(this.deliveryAgentRepo.availableDeliveryAgents(), HttpStatusCode.valueOf(200));
    }

    @Transactional
    public ResponseEntity updateDeliveryAgent(DeliveryAgent deliveryAgent) {

        try{
            DeliveryAgent queryDeliveryAgent = deliveryAgentRepo.findByDeliveryAgentName(deliveryAgent.getName());
            if (null == queryDeliveryAgent) {
                if(deliveryAgent.getStatus() == null){
                    deliveryAgent.setStatus(Constants.deliveryStatus.AVAILABLE.toString());
                }
                deliveryAgentRepo.save(deliveryAgent);
            } else {
                if(deliveryAgent.getStatus() == null){
                    deliveryAgent.setStatus(Constants.deliveryStatus.AVAILABLE.toString());
                }
                deliveryAgentRepo.updateAgent(deliveryAgent.getPhone(),deliveryAgent.getStatus(),queryDeliveryAgent.getId());
            }
            queryDeliveryAgent = deliveryAgentRepo.findByDeliveryAgentName(deliveryAgent.getName());
            return new ResponseEntity<>(queryDeliveryAgent, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }




    private ResponseEntity<String> updateDeliveryAgentStatus(Long deliveryAgentID, Constants.deliveryStatus deliveryStatus) {
        try {
            DeliveryAgent deliveryAgent = this.deliveryAgentRepo.getReferenceById(deliveryAgentID);
            deliveryAgent.setStatus(deliveryStatus.toString());
            this.deliveryAgentRepo.save(deliveryAgent);

            return new ResponseEntity<>("Delivery Agent Status Updated.", HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    public ResponseEntity<List<DeliveryAgentOrders>> getDeliveryAgentOrders(Integer deliveryAgentId) {
        try {
            List<DeliveryAgentOrders> deliveryAgentOrders = this.deliveryAgentOrdersRepo.getDeliveryAgentOrdersById(deliveryAgentId);
            return new ResponseEntity<>(deliveryAgentOrders, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    public ResponseEntity<List<DeliveryAgentOrders>> getDeliveryAgentCurrentOrders(Integer deliveryAgentId) {
        try {
            List<DeliveryAgentOrders> deliveryAgentOrders = this.deliveryAgentOrdersRepo.getDeliveryAgentCurrentOrdersById(deliveryAgentId);
            return new ResponseEntity<>(deliveryAgentOrders, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    private ResponseEntity<String> updateOrderStatus(String orderId, String orderStatus, Boolean updateTime) {
        try {
            CustomerOrder customerOrder = this.customerOrderRepo.getReferenceById(orderId);
            customerOrder.setOrder_status(orderStatus);
            if (updateTime) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = new Date();
                customerOrder.setDelivery_time(formatter.format(date).toString());
            }
            this.customerOrderRepo.save(customerOrder);

            return new ResponseEntity<>("Customer Order Status Updated.", HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    private ResponseEntity<String> assignDeliveryAgentToCustomerOrder(String orderId, Long deliveryAgentID) {
        try {
            CustomerOrder customerOrder = this.customerOrderRepo.getReferenceById(orderId);
            customerOrder.setDelivery_agent_id(Math.toIntExact(deliveryAgentID));
            this.customerOrderRepo.save(customerOrder);

            return new ResponseEntity<>("Customer Order Status Assigned to the Delivery Agent.", HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    public void deliverOrder(String orderId) {

        try {
            List<DeliveryAgent> deliveryAgentList = null;
            boolean isAvailable = false;

            while (!isAvailable) {
                deliveryAgentList = (List<DeliveryAgent>) getAvailableDeliveryAgents().getBody();
                if (deliveryAgentList.size() > 0) {
                    isAvailable = true;
                } else {
                    Thread.sleep(5000);
                }
            }

            DeliveryAgent currentDeliveryAgent = deliveryAgentList.get(0);

            assignDeliveryAgentToCustomerOrder(orderId, currentDeliveryAgent.getId());
            updateOrderStatus(orderId, Constants.orderStatus.OUT_FOR_DELIVERY.toString(), false);
            updateDeliveryAgentStatus(currentDeliveryAgent.getId(), Constants.deliveryStatus.IN_PROGRESS);

            Thread.sleep(60000);

            updateOrderStatus(orderId, Constants.orderStatus.DELIVERED.toString(), true);
            updateDeliveryAgentStatus(currentDeliveryAgent.getId(), Constants.deliveryStatus.AVAILABLE);



        } catch (Exception e) {
            System.out.println("Error in Delivering the Order:" + orderId);
        }

    }

}

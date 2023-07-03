package com.ds.avengers.controller;

import com.ds.avengers.dto.DeliveryAgent;
import com.ds.avengers.dto.DeliveryAgentOrders;
import com.ds.avengers.repo.IDeliveryAgentRepo;
import com.ds.avengers.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("delivery/api/v1")
public class DeliveryController {

    @Autowired
    private IDeliveryAgentRepo deliveryAgentRepo;

    @Autowired
    private DeliveryService deliveryService;


    @GetMapping("/delivery-agents")
    public List<DeliveryAgent> listAll(Model model){
        List<DeliveryAgent> deliveryAgentList = deliveryAgentRepo.findAll();
        model.addAttribute("listDeliveryAgents", deliveryAgentList);
        return deliveryAgentList;
    }

    @GetMapping("/available-delivery-agents")
    public ResponseEntity getAvailableAgents(){
        return deliveryService.getAvailableDeliveryAgents();
    }

    @PostMapping("/delivery-agent")
    public ResponseEntity addDeliveryAgent(@RequestBody DeliveryAgent deliveryAgent) {
        return deliveryService.addDeliveryAgent(deliveryAgent);
    }

    @PutMapping("/delivery-agent")
    public ResponseEntity updateDeliverAgent(@RequestBody DeliveryAgent deliveryAgent) {
        return deliveryService.updateDeliveryAgent(deliveryAgent);
    }

    @RequestMapping(value="/delivery-agent/orders/{deliveryAgentId}",method= RequestMethod.GET)
    public ResponseEntity<List<DeliveryAgentOrders>> getDeliveryAgentOrders(@PathVariable("deliveryAgentId") Integer deliveryAgentId) {

        return this.deliveryService.getDeliveryAgentOrders(deliveryAgentId);
    }

    @RequestMapping(value="/delivery-agent/current-orders/{deliveryAgentId}",method= RequestMethod.GET)
    public ResponseEntity<List<DeliveryAgentOrders>> getDeliveryAgentCurrentOrders(@PathVariable("deliveryAgentId") Integer deliveryAgentId) {

        return this.deliveryService.getDeliveryAgentCurrentOrders(deliveryAgentId);
    }
}

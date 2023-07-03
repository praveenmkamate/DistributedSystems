package com.ds.avengers.repo;

import com.ds.avengers.dto.DeliveryAgentOrders;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDeliveryAgentOrdersRepo extends ReadOnlyRepo<DeliveryAgentOrders, Long>{
    @Query("FROM DeliveryAgentOrders dao WHERE dao.delivery_agent_id = ?1 AND dao.order_status='DELIVERED'")
    List<DeliveryAgentOrders> getDeliveryAgentOrdersById(Integer delivery_agent_id);

    @Query("FROM DeliveryAgentOrders dao WHERE dao.delivery_agent_id = ?1 AND dao.order_status='OUT FOR DELIVERY'")
    List<DeliveryAgentOrders> getDeliveryAgentCurrentOrdersById(Integer delivery_agent_id);
}

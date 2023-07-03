package com.ds.avengers.dto;

import java.util.List;

public class CustomerOrderInformation {
    private CustomerOrder customerOrder;
    private List<CustomerOrderItem> customerOrderItems;

    public CustomerOrderInformation(CustomerOrder customerOrder, List<CustomerOrderItem> customerOrderItems) {
        this.customerOrder = customerOrder;
        this.customerOrderItems = customerOrderItems;
    }

    public CustomerOrderInformation() {
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public List<CustomerOrderItem> getCustomerOrderItems() {
        return customerOrderItems;
    }

    public void setCustomerOrderItems(List<CustomerOrderItem> customerOrderItems) {
        this.customerOrderItems = customerOrderItems;
    }
}

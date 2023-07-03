package com.ds.avengers.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "food_order_item", schema = "food_app")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer item_id;
    private Integer category_id;
    private Integer order_id;
    private Integer count;

    public OrderDetails(Integer item_id, Integer category_id, Integer order_id, Integer count) {
        this.item_id = item_id;
        this.category_id = category_id;
        this.order_id = order_id;
        this.count = count;
    }

    public OrderDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

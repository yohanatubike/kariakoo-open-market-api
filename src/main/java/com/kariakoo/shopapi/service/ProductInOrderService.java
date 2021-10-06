package com.kariakoo.shopapi.service;

import com.kariakoo.shopapi.entity.ProductInOrder;
import com.kariakoo.shopapi.entity.User;

public interface ProductInOrderService {
    void update(String itemId, Integer quantity, User user);
    ProductInOrder findOne(String itemId, User user);
}

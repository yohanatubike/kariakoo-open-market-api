package com.kariakoo.shopapi.service;

import com.kariakoo.shopapi.entity.Cart;
import com.kariakoo.shopapi.entity.ProductInOrder;
import com.kariakoo.shopapi.entity.User;

import java.util.Collection;

public interface CartService {
    Cart getCart(User user);

    void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user);

    void delete(String itemId, User user);

    void checkout(User user);
}

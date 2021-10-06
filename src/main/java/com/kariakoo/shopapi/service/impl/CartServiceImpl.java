package com.kariakoo.shopapi.service.impl;

import com.kariakoo.shopapi.entity.Cart;
import com.kariakoo.shopapi.entity.OrderMain;
import com.kariakoo.shopapi.entity.ProductInOrder;
import com.kariakoo.shopapi.entity.User;
import com.kariakoo.shopapi.repository.OrderRepository;
import com.kariakoo.shopapi.repository.ProductInOrderRepository;
import com.kariakoo.shopapi.repository.UserRepository;
import com.kariakoo.shopapi.service.CartService;
import com.kariakoo.shopapi.service.ProductService;
import com.kariakoo.shopapi.service.UserService;
import com.kariakoo.shopapi.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductInOrderRepository productInOrderRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;

    @Override
    public Cart getCart(User user) {
        return user.getCart();
    }

    @Override
    @Transactional
    public void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user) {
        Cart finalCart = user.getCart();
        productInOrders.forEach(productInOrder -> {
            Set<ProductInOrder> set = finalCart.getProducts();
            Optional<ProductInOrder> old = set.stream()
                    .filter(e -> e.getProductId()
                            .equals(productInOrder.getProductId()))
                    .findFirst();
            ProductInOrder prod;
            if (old.isPresent()) {
                prod = old.get();
                prod.setCount(productInOrder.getCount() + prod.getCount());
            } else {
                prod = productInOrder;
                prod.setCart(finalCart);
                finalCart.getProducts().add(prod);
            }
            productInOrderRepository.save(prod);
        });
        cartRepository.save(finalCart);
    }

    @Override
    @Transactional
    public void delete(String itemId, User user) {
        Optional<ProductInOrder> op = user.getCart()
                .getProducts().stream()
                .filter(e -> itemId.equals(e.getProductId()))
                .findFirst();
        op.ifPresent(productInOrder -> {
            productInOrder.setCart(null);
            productInOrderRepository.deleteById(productInOrder.getId());
        });
    }

    @Override
    @Transactional
    public void checkout(User user) {
        // Creat an order
        OrderMain order = new OrderMain(user);
        orderRepository.save(order);

        // clear cart's foreign key & set order's foreign key& decrease stock
        user.getCart().getProducts().forEach(productInOrder -> {
            productInOrder.setCart(null);
            productInOrder.setOrderMain(order);
            productService.decreaseStock(productInOrder.getProductId(), productInOrder.getCount());
            productInOrderRepository.save(productInOrder);
        });

    }
}

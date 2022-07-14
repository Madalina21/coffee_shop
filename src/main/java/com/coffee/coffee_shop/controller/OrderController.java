package com.coffee.coffee_shop.controller;

import com.coffee.coffee_shop.model.Order;
import com.coffee.coffee_shop.model.OrderStatus;
import com.coffee.coffee_shop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> findAll(){
        return orderService.findAll();
    }

    // View orders for a specific user
    @GetMapping("user/{userId}")
    public List<Order> findOrdersByUserId(@PathVariable long userId) {
        return orderService.findOrdersByUserId(userId);
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable long id){
        return orderService.findById(id);
    }

    @PostMapping
    public Order save(@RequestBody Order order) {
        return orderService.save(order);
    }

    @PutMapping("/{id}/updateStatus")
    public void updateStatus(@PathVariable Long id, @RequestBody OrderStatus newOrderStatus) {
        orderService.updateStatus(id, newOrderStatus);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {orderService.deleteById(id); }

}

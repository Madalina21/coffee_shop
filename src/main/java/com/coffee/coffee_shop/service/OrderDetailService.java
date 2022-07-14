package com.coffee.coffee_shop.service;

import com.coffee.coffee_shop.model.OrderDetail;
import com.coffee.coffee_shop.repository.OrderDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailsRepository;

    public List<OrderDetail> findAll() {
        return orderDetailsRepository.findAll();
    }

    public List<OrderDetail> findByOrderId(long orderId) {
        return orderDetailsRepository.findByOrderId(orderId);
    }

}

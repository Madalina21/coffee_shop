package com.coffee.coffee_shop.repository;

import com.coffee.coffee_shop.model.Order;
import com.coffee.coffee_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

}

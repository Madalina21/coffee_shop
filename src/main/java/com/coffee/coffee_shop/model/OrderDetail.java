package com.coffee.coffee_shop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "quantity", nullable = false)
    private short quantity;

    @Column(name = "unit_price", nullable = false)
    private float unitPrice;

    @Column(name = "subtotal")
    @Formula("unit_price * quantity")//la fel ca la cart doar ca apare in tabel
    private float subtotal;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore//pt legatura infinita order-order_details=> doar din sens opus
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
package com.nguyeen.springlinhtinh.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "price", nullable = false)
    Float price;

    @Column(name = "number_of_products", nullable = false)
    int numberOfProducts;

    @Column(name = "total_money", nullable = false)
    Float totalMoney;

}

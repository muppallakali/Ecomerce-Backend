package com.Kali.billingSoftware.entity;

import com.Kali.billingSoftware.io.PaymentDetails;
import com.Kali.billingSoftware.io.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity>items=new ArrayList<>();
    private String orderId;
    private String phoneNumber;
    private Double subTotal;
    private Double tax;
    private Double grandTotal;
    @Embedded
    private PaymentDetails paymentDetails;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate(){
        this.orderId="ORD"+System.currentTimeMillis();
        this.createdAt=LocalDateTime.now();
    }
}

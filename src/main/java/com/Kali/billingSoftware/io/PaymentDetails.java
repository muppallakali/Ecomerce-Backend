package com.Kali.billingSoftware.io;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
//    private  String stripeOrderId;
//    private String stripePaymentId;
//    private String stripeSignature;
    private PaymentStatus status;
    public enum PaymentStatus{
        PENDING,COMPLETED,FAILED
    }
}

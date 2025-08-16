package com.Kali.billingSoftware.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentVerificationRequest {
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
//private String stripeOrderId;
//    private String stripePaymentId;
//    private String stripeSignature;
    private String orderId;
}

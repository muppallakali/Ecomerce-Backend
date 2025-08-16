package com.Kali.billingSoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StripeOrderResponse {
    private String id;
    private String payment_intent;
    private Long amount;
    private String currency;
    private String status;
    private Date created_at;
}

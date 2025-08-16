package com.Kali.billingSoftware.service;
import com.Kali.billingSoftware.io.StripeOrderResponse;

public interface StripeService {

        StripeOrderResponse createOrder(Long amount, String currency) throws Exception;


}

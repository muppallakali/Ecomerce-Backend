package com.Kali.billingSoftware.controller;

import com.Kali.billingSoftware.io.OrderResponse;
import com.Kali.billingSoftware.io.PaymentRequest;
import com.Kali.billingSoftware.io.PaymentVerificationRequest;
import com.Kali.billingSoftware.io.RazorpayOrderResponse;
import com.Kali.billingSoftware.service.OrderService;
import com.Kali.billingSoftware.service.RazorpayService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final RazorpayService razorpayService;
    private final OrderService orderService;
    @PostMapping("/createOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorpayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest request)throws RazorpayException{
        return razorpayService.createOrder(request.getAmount(),request.getCurrency());
    }

    @PostMapping("/verify")
    public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request){
        return orderService.verifyPayment(request);
    }
}


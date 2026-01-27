package com.couriersphere.controller;

import com.couriersphere.entity.Courier;
import com.couriersphere.enums.PaymentStatus;
import com.couriersphere.repository.CourierRepository;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentWebhookController {

    private final CourierRepository courierRepository;

    public PaymentWebhookController(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @PostMapping("/webhook")
    public void handleRazorpayWebhook(@RequestBody String payload) {
        JSONObject json = new JSONObject(payload);
        String event = json.getString("event");

        // Logic for successful payment event
        if ("order.paid".equals(event)) {
            JSONObject paymentEntity = json.getJSONObject("payload")
                                           .getJSONObject("order")
                                           .getJSONObject("entity");
            
            String razorpayOrderId = paymentEntity.getString("id");

            // Find the courier associated with this Razorpay Order ID
            courierRepository.findByRazorpayOrderId(razorpayOrderId).ifPresent(courier -> {
                courier.setPaymentStatus(PaymentStatus.SUCCESS);
                courierRepository.save(courier);
            });
        }
    }
}
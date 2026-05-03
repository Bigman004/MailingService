package com.example.mailingService.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("paymentService")
public interface OrderInterface {

}

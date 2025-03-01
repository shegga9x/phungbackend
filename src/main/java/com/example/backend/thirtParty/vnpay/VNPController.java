package com.example.backend.thirtParty.vnpay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.backend.util.Client;

import jakarta.servlet.http.HttpServletRequest;

@Client
@RestController
@RequestMapping(value = "/api/v1/vnp")
public class VNPController {
    @Autowired
    private VNPAYService vnPayService;

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam Long userId, @RequestParam BigDecimal amount,
            HttpServletRequest request) throws UnsupportedEncodingException {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, amount, userId, baseUrl);
        System.out.println(vnpayUrl);
        return vnpayUrl;
    }

    @GetMapping("/vnpay-payment-return")
    public RedirectView paymentCompleted(HttpServletRequest request) {
        String url = vnPayService.orderReturn(request) == 1 ? "/balance?result=fail"
                : "/balance?result=success";
        return new RedirectView(url);
    }
}

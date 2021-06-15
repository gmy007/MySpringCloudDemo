package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.service.impl.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {
    @Autowired
    PaymentServiceImpl paymentService;
    @Value("${server.port}")
    private  String SERVER_PORT;
    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment) {
        int ret = paymentService.create(payment);
        log.info("******插入结果为："+ret);
        if(ret>0){
            return new CommonResult(200,"插入成功");
        }else
            return new CommonResult(400,"插入数据库失败");
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment ret = paymentService.getPaymentById(id);
        log.info("******查询结果为："+ret);
        if(ret != null){
            return new CommonResult(200,"查询数据库成功O(∩_∩)O哈哈~  Server port:"+SERVER_PORT,ret);
        }else
            return new CommonResult(400,"查询数据库失败");
    }
    @GetMapping(value = "/lb")
    public String getPaymentLB() {
        return SERVER_PORT;
    }
}

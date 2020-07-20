package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.service.impl.PaymentServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {
    @Autowired
    PaymentServiceImpl paymentService;
    @Value("${server.port}")
    private  String SERVER_PORT;

    @Resource
    private DiscoveryClient discoveryClient;//服务发现信息
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

    @GetMapping("/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*******"+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("/timeout")
    public CommonResult timeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

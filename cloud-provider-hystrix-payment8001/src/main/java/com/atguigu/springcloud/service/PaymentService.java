package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "当前线程为：  "+Thread.currentThread().getName()+"paymentInfo_OK,ID:   "+id+"\t"+"_(:з」∠)_";
    }

    @HystrixCommand(fallbackMethod = "payment_TimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })//兜底策略，超时3秒或出现异常，就会调用兜底方案执行
    public String paymentInfo_Timeout(Integer id){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i=1/0;
        return "当前线程为：  "+Thread.currentThread().getName()+"paymentTimeout,ID:   "+id+"\t"+"_(:з」∠)_";
    }
    public String payment_TimeoutHandler(Integer id){
        return "o(╥﹏╥)o 调用服务接口超时或异常：\t"+"\t当前线程："+Thread.currentThread().getName()+"  id:"+id;
    }

}

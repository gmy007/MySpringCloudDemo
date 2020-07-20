package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class MyRoundLoadBalance implements MyLoadBalance {

    private AtomicInteger atomicInteger =new AtomicInteger(0);
    private  final int getAndIncrement(){
        int cur;
        int next;
        do{
             cur=atomicInteger.get();
             next=cur>=Integer.MAX_VALUE? 0: cur+1;
        }while (!atomicInteger.compareAndSet(cur,next));
        System.out.println("*****这是第"+next+"访问");
        return next;
    }

    @Override
    public ServiceInstance instance(List<ServiceInstance> instances) {

        return instances.get(getAndIncrement()%instances.size());
    }
}

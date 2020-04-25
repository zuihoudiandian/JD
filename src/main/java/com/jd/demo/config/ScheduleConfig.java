package com.jd.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

@Configuration
//所有的定时任务都放在一个线程池中，定时任务启动时使用不同都线程。
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5,
//                new BasicThreadFactory.Builder().namingPattern("task-schedule-pool-%d").daemon(true).build());
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
//        taskRegistrar.setScheduler(executorService);
    }
}

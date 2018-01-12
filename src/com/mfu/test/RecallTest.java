package com.mfu.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;

public class RecallTest {
    @Autowired
    private TaskScheduler scheduler;

    public void init() {
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                myMethod();
            }
        }, new Date(), 5000); //This will start now and run every two hours
    }

    public void myMethod() {
        // the method you want to invoke
    	System.out.println("Call myMethod");
    }
}
package com.example.demo.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class ToolUtils {

    public static boolean checkParamException(Boolean b,String ... strings){
        boolean tag = b.booleanValue();
        AtomicInteger atomicInteger = new AtomicInteger(0);

        for(String object: strings){
            if(object == null || "".equals(object) ){
                atomicInteger.incrementAndGet();;
            }
        }
        if(atomicInteger.get() == strings.length){
            return false;
        }else if(atomicInteger.get() > 0){
            return tag;
        }
        System.out.println("nnnnnnnn");

        return true;
    }
}

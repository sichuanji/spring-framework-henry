package test;

import annotation.MyOverride;

public class Animal {
    @MyOverride("animal")
    public void  speak(){
        System.out.println("animal sound");
    }
}

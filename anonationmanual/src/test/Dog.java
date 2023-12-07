package test;

import annotation.MyOverride;

public class Dog extends Animal{
    @MyOverride("dog")
    public void speak(){
        System.out.println("汪汪");
    }

}

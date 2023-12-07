package test;

import annotationProcessor.OverrideProcessor;

public class Test {
    public static void main(String[] args) {
        OverrideProcessor overrideProcessor = new OverrideProcessor();
//        overrideProcessor.checkOverrides(Dog.class);
        Dog dog = new Dog();
    }
}

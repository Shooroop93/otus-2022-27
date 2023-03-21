package ru.sergeev.exampleTwo;

import ru.sergeev.annotation.Log;

public class TestLoggingImplTwo implements TestLoggingInterfaceTwo {

    @Log
    @Override
    public void calculation(int param) {
        System.out.println("test 12345" + param);
    }

    @Log
    @Override
    public void calculation(int paramOne, int paramTwo) {
        System.out.println("One: " + paramOne + " Two: " + paramTwo);
    }

    @Log
    @Override
    public void calculation(int paramOne, int paramTwo, int paramThree) {
        System.out.println("One: " + paramOne + " Two: " + paramTwo + " Three: " + paramThree );
    }
}
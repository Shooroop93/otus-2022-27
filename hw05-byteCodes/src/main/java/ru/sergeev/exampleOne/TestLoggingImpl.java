package ru.sergeev.exampleOne;

import ru.sergeev.annotation.Log;

public class TestLoggingImpl implements TestLoggingInterfaceOne {

    @Log
    @Override
    public void calculation(int param) {
        System.out.println("calculation(int param) " + param);
    }

    @Log
    @Override
    public void calculation(int paramOne, int paramTwo) {
        System.out.println("calculation(int paramOne, int paramTwo) = " + (paramOne + paramTwo));
    }

    // Не ставлю @Log, для теста.
    @Override
    public void calculation(int paramOne, int paramTwo, int paramThree) {
        System.out.println("calculation(int paramOne, int paramTwo, int paramThree) = " + (paramOne + paramTwo + paramThree));
    }
}
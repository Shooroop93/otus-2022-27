package ru.sergeev;

import ru.sergeev.exampleOne.TestLoggingImpl;
import ru.sergeev.exampleOne.TestLoggingInterfaceOne;
import ru.sergeev.exampleTwo.FatherInterfaceLogger;
import ru.sergeev.exampleTwo.TestLoggingImplTwo;
import ru.sergeev.exampleTwo.TestLoggingInterfaceTwo;
import ru.sergeev.logger.Ioc;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("----------------------------------------------------------");

        TestLoggingInterfaceOne myClass1 = Ioc.getInstance(new TestLoggingImpl());
        myClass1.calculation(1);
        myClass1.calculation(1, 2);
        myClass1.calculation(1, 2, 3);

        System.out.println("----------------------------------------------------------");

        FatherInterfaceLogger myClass2 = Ioc.getInstance(new TestLoggingImplTwo());
        myClass2.calculation(12331241);
        myClass2.calculation(123124, 12312412);
        myClass2.calculation(123123, 1241412, 123412);

        System.out.println("----------------------------------------------------------");

        TestLoggingInterfaceTwo myClass3 = Ioc.getInstance(new TestLoggingImplTwo());
        myClass3.calculation(346324);
        myClass3.calculation(345345346, 56346);
        myClass3.calculation(7548658, 5234532, 234234);

        System.out.println("----------------------------------------------------------");

    }
}
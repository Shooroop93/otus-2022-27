package ru.sergeev;

import ru.sergeev.exampleOne.TestLoggingImpl;
import ru.sergeev.exampleTwo.TestLoggingImplTwo;
import ru.sergeev.logger.Ioc;

public class MainClass {
    public static void main(String[] args) {
        FatherInterfaceLogger myClass = Ioc.createMyClass(new TestLoggingImpl());
        myClass.calculation(1);
        myClass.calculation(1, 2);
        myClass.calculation(1, 2, 3);

        FatherInterfaceLogger myClass1 = Ioc.createMyClass(new TestLoggingImplTwo());
        myClass1.calculation(12331241);
        myClass1.calculation(123124, 12312412);
        myClass1.calculation(123123, 1241412, 123412);
    }
}
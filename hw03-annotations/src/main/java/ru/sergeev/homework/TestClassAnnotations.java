package ru.sergeev.homework;

import ru.sergeev.homework.annotations.After;
import ru.sergeev.homework.annotations.Before;
import ru.sergeev.homework.annotations.Test;

public class TestClassAnnotations {

    @Test
    void test1() {
        System.out.println("test1");
    }

    @Test
    void test2() {
        System.out.println("test2");
    }

    @Before
    void before1() {
        System.out.println("before1");
    }

    @Before
    void before2() {
        System.out.println("before1");
    }

    @After
    void after1() {
        System.out.println("after1");
    }
}
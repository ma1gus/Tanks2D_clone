package com.utils;

public class Time {

    public static final long SECOND = 1000000000l;//количество наносекунд в секунде

    public static long get() {

        return System.nanoTime();
    }
}

package com.example.fighter;

@FunctionalInterface
public interface Responsble<T> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     */
    void callback(T responce);
}
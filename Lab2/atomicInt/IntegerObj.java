/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab2.atomicInt;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Andrew Guerin
 */
class IntegerObj {
    int value;

    private final AtomicInteger counter = new AtomicInteger(0);

    public int getValue() {
        return counter.get();
    }

    IntegerObj(int val) {
        this.value = val;
    }
    int inc(){

        value = getValue();
        int newValue = value + 1;
        if(counter.compareAndSet(value, newValue)){
           return newValue;
        }

        this.value++;
        return this.value;
    }
}

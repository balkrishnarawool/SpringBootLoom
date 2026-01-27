package com.balarawool.bootloom.abank.joiners;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.stream.Stream;

public class CollectingJoiner<T> implements Joiner<T, Stream<T>> {

    private final Queue<T> results = new ConcurrentLinkedQueue<>();

    public boolean onComplete(Subtask<? extends T> subtask) {
        System.out.println("Completed "+subtask);
        System.out.println(subtask.get());
        if (subtask.state() == Subtask.State.SUCCESS) {
            results.add(subtask.get());
        }
        return false;
    }

    public boolean onFork(Subtask<? extends T> subtask) {
        System.out.println("Forking "+subtask);
        return false;
    }

    public Stream<T> result() {
        return results.stream();
    }

}

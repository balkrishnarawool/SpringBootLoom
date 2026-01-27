package com.balarawool.bootloom.abank.controller;

import com.balarawool.bootloom.abank.joiners.CollectingJoiner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.StructuredTaskScope;
import java.util.stream.Collectors;

@RestController
public class ResultJoinerController {

    @GetMapping("/join-result")
    public String joinResult() {

        try (var scope = StructuredTaskScope.open(new CollectingJoiner<String>())) {
            scope.fork(() -> "A");
            scope.fork(() -> "B");
            return scope.join().collect(Collectors.joining());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

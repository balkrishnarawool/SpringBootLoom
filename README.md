# SpringBoot and Project Loom

This repo shows how Project Loom's features can be used in SpringBoot apps.
It contains source code for 2 of my talks:
- Virtual Threads, Structured Concurrency and Scoped Values: Putting it all together
  - This talk explains the 3 main features of Project Loom and explores their API
- Getting practical with Virtual Threads and Structured Concurrency
  - This talk builds on the previous examples, shows various scenarios and explores how custom Joiners can come in handy

[If you have any questions, comments or feedback, contact me at [@balarawool.bsky.social](https://bsky.app/profile/balarawool.bsky.social)]

## Description

This Maven project has two modules: `abank` and `services`
- `abank` module is a spring-boot app that makes use of features of Project Loom: Virtual Threads, Structured Concurrency and Scoped Values.
- `services` module is another a spring-boot app that contains external services used by `abank` module with some dummy responses.

## Branches

There are 5 branches
- `main`
- `with-structured-concurrency` shows an implementation of 'loan application' use-case with Structured Concurrency API
- `with-completbale-future` shows an implementation of the same use-case with CompletableFuture API
- `with-scoped-values` shows an implementation of another use-case with ScopedValues API
- `custom-joiners` shows few examples of Custom Joiners and their usage

## Pre-requisites

This project requires JDK 25.
Also, make sure to set `--enable-preview` as VM argument while running these spring-boot apps.

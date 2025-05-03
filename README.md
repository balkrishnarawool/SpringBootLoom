# Virtual Threads, Structured Concurrency and Scoped Values: Putting it all together

This is source code for my talk 'Virtual Threads, Structured Concurrency and Scoped Values: Putting it all together'.
If you have any questions, comments or feedback, contact me at [@balarawool.bsky.social](https://bsky.app/profile/balarawool.bsky.social)

## Description

This Maven project has two modules: `abank` and `services`
- `abank` module is a spring-boot app that makes use of features of Project Loom: Virtual Threads, Structured Concurrency and Scoped Values.
- `services` module is another a spring-boot app that contains external services used by `abank` module with some dummy responses.

## Pre-requisites

This project requires JDK 24 (Project Loom early access build).
You can download Project Loom early access builds from [https://jdk.java.net/loom/](https://jdk.java.net/loom/)

Also, make sure to set `--enable-preview` as VM argument while running these spring-boot apps.

package com.ll.global.app;

import java.util.Scanner;

public class App {
    private final Scanner scanner;

    public App(final Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        System.out.print("명언 : ");
        System.out.print("작가 : ");
    }
}

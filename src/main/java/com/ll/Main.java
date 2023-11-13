package com.ll;

import com.ll.global.app.App;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final App app = new App(scanner);
        app.run();
    }
}
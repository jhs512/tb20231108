package com.ll.global.app;

import com.ll.domain.quotation.quotation.controller.QuotationController;
import com.ll.global.rq.Rq;

import java.util.Scanner;

public class App {
    private final Scanner scanner;

    public App(final Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        final QuotationController quotationController = new QuotationController(scanner);

        while (true) {
            final String cmd = scanner.nextLine().trim();

            final Rq rq = new Rq(cmd);

            switch (rq.getAction()) {
                case "삭제", "수정", "등록", "목록" -> quotationController.dispatch(rq);
                case "종료" -> {
                    return;
                }
            }
        }
    }
}

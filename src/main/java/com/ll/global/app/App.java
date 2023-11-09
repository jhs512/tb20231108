package com.ll.global.app;

import com.ll.domain.quotation.quotation.entity.Quotation;
import com.ll.global.rq.Rq;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private final Scanner scanner;
    final List<Quotation> quotations;
    long lastQuotationId;

    public App(final Scanner scanner) {
        this.scanner = scanner;
        quotations = new ArrayList<>();
        lastQuotationId = 0;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            final String cmd = scanner.nextLine().trim();

            final Rq rq = new Rq(cmd);
            final String action = rq.getAction();

            switch (action) {
                case "삭제" -> {
                    actionRemove(rq);
                }
                case "수정" -> {
                    actionModify(rq);
                }
                case "등록" -> {
                    actionWrite();
                }
                case "목록" -> {
                    actionShowList();
                }
                case "종료" -> {
                    return;
                }
            }
        }
    }

    private void actionRemove(final Rq rq) {
        final long id = rq.getParameterAsLong("id", 0);

        quotations
                .stream()
                .filter(quotation -> quotation.getId() == id)
                .findFirst()
                .ifPresentOrElse(
                        quotation -> {
                            quotations.remove(quotation);
                            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
                        },
                        () -> System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id))
                );
    }

    private void actionModify(final Rq rq) {
        final long id = rq.getParameterAsLong("id", 0);

        quotations
                .stream()
                .filter(_quotation -> _quotation.getId() == id)
                .findFirst()
                .ifPresentOrElse(
                        quotation -> {
                            System.out.println("명언(기존) : %s".formatted(quotation.getContent()));
                            System.out.print("명언 : ");
                            final String content = scanner.nextLine().trim();
                            System.out.println("작가(기존) : %s".formatted(quotation.getAuthorName()));
                            System.out.print("작가 : ");
                            final String authorName = scanner.nextLine().trim();

                            quotation.setContent(content);
                            quotation.setAuthorName(authorName);

                            System.out.println("%d번 명언이 수정되었습니다.".formatted(id));
                        },
                        () -> System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id))
                );
    }

    private void actionShowList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        quotations
                .reversed()
                .forEach(
                        quotation -> System.out.println(
                                "%d / %s / %s".formatted(
                                        quotation.getId(),
                                        quotation.getAuthorName(),
                                        quotation.getContent()
                                )
                        )
                );
    }

    private void actionWrite() {
        System.out.print("명언 : ");
        final String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        final String authorName = scanner.nextLine().trim();

        final long id = ++lastQuotationId;

        Quotation quotation = new Quotation(id, authorName, content);
        quotations.add(quotation);

        System.out.println("%d번 명언이 등록되었습니다.".formatted(id));
    }
}

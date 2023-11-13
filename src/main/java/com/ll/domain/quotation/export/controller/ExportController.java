package com.ll.domain.quotation.export.controller;

import com.ll.domain.quotation.export.service.ExportService;
import com.ll.global.rq.Rq;

import java.util.Scanner;

public class ExportController {
    private final Scanner scanner;
    private final ExportService exportService;

    public ExportController(final Scanner scanner) {
        this.scanner = scanner;
        exportService = new ExportService();
    }

    public void dispatch(Rq rq) {
        switch (rq.getAction()) {
            case "빌드" -> actionExport(rq);
        }
    }

    private void actionExport(Rq rq) {
        exportService.export();

        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}

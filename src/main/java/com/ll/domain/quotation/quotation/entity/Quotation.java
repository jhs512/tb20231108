package com.ll.domain.quotation.quotation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Quotation {
    private final long id;
    @Setter
    private String authorName;
    @Setter
    private String content;
}

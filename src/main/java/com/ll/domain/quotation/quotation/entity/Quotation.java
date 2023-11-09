package com.ll.domain.quotation.quotation.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Quotation {
    @Setter
    private Long id;
    @Setter
    @NonNull
    private String authorName;
    @Setter
    @NonNull
    private String content;
}

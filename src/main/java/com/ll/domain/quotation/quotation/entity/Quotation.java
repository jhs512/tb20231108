package com.ll.domain.quotation.quotation.entity;

import lombok.*;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
public class Quotation {
    @Setter
    @EqualsAndHashCode.Include
    private Long id;
    @Setter
    @NonNull
    private String authorName;
    @Setter
    @NonNull
    private String content;
}

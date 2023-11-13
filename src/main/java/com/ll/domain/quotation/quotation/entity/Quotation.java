package com.ll.domain.quotation.quotation.entity;

import lombok.*;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quotation {
    @Setter
    private Long id;
    @Setter
    @NonNull
    private String authorName;
    @Setter
    @NonNull
    private String content;

    @Override
    public String toString() {
        return "Quotation{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Quotation quotation = (Quotation) object;

        return Objects.equals(id, quotation.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

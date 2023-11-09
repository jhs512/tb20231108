package com.ll.global.rq;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RqTest {
    @Test
    @DisplayName("t1")
    void t1() {
        final Rq rq = new Rq("삭제?id=1");

        assertThat(rq.getAction()).isEqualTo("삭제");
    }
}

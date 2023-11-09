package com.ll.global.rq;

public class Rq {
    private final String action;

    public Rq(final String cmd) {
        final String[] cmdBits = cmd.split("\\?", 2);
        action = cmdBits[0].trim();
    }

    public String getAction() {
        return action;
    }
}

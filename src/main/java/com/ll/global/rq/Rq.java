package com.ll.global.rq;

import java.util.HashMap;
import java.util.Map;

public class Rq {
    private final String action;
    private final String queryString;
    private Map<String, String> params;

    public Rq(final String cmd) {
        final String[] cmdBits = cmd.split("\\?", 2);
        action = cmdBits[0].trim();
        queryString = cmdBits.length == 2 ? cmdBits[1].trim() : "";
        params = new HashMap<>();

        if (queryString.isBlank()) return;

        final String[] paramBits = queryString.split("=", 2);
        final String paramName = paramBits[0].trim();
        final String paramValue = paramBits[1].trim();

        params.put(paramName, paramValue);
    }

    public String getAction() {
        return action;
    }

    public String getParameter(final String paramName) {
        return params.get(paramName);
    }
}

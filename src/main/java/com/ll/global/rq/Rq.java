package com.ll.global.rq;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Rq {
    private final String action;
    private final String queryString;
    private Map<String, String> params;

    public Rq(final String cmd) {
        final String[] cmdBits = cmd.split("\\?", 2);
        action = cmdBits[0].trim();
        queryString = cmdBits.length == 2 ? cmdBits[1].trim() : "";
        params = Arrays.stream(queryString.split("&"))
                .filter(param -> param.contains("="))
                .map(param -> param.split("=", 2))
                .collect(Collectors.toMap(
                        paramBits -> paramBits[0].trim(),
                        paramBits -> paramBits[1].trim(),
                        (existing, replacement) -> replacement));
    }

    public String getAction() {
        return action;
    }

    public String getParameter(final String paramName) {
        return params.get(paramName);
    }

    public String getParameter(final String paramName, final String defaultValue) {
        return null;
    }
}

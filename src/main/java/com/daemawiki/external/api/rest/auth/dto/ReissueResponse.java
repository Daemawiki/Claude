package com.daemawiki.external.api.rest.auth.dto;

public record ReissueResponse(
        String token
) {
    public static ReissueResponse create(String token) {
        return new ReissueResponse(token);
    }
}

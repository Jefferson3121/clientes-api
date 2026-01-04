package com.ClientHub.api.dto.response;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

public record ResponseError(int status, String message,LocalDateTime timestamp) {
}

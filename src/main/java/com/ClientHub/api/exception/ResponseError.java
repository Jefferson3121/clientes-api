package com.ClientHub.api.exception;

import java.time.LocalDateTime;

public record ResponseError(int status, String message, LocalDateTime timestamp) {
}

package com.ClientHub.api.dto.response;

import java.time.LocalDateTime;

public record ResponseError(int status, String message, LocalDateTime timestamp) {
}

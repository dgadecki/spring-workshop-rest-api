package pl.dgadecki.springworkshoprestapi.common.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ErrorResponse(
        @Schema(description = "Unique error ID", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID uuid,
        @Schema(description = "Timestamp errors occur", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime timestamp,
        @Schema(description = "Path for which the error occurred", requiredMode = Schema.RequiredMode.REQUIRED)
        String requestPath,
        @Schema(description = "Error message with error information", requiredMode = Schema.RequiredMode.REQUIRED)
        String message,
        @Schema(description = "Additional details for the error. Details are included in the error response only when they occur.")
        String details
) { }

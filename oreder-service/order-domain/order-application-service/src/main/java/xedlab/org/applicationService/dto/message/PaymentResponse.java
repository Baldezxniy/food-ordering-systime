package xedlab.org.applicationService.dto.message;

import org.xedlab.commonDomain.valueobject.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record PaymentResponse(
        String id,
        String sagaId,
        String orderId,
        String paymentId,
        String customerId,
        BigDecimal price,
        Instant createdAt,
        PaymentStatus paymentStatus,
        List<String> failureMessages
) {
}

package xedlab.org.applicationService.dto.message;

import org.xedlab.commonDomain.valueobject.OrderApprovalStatus;

import java.time.Instant;
import java.util.List;

public record RestaurantApprovalResponse(
        String id,
        String sagaId,
        String orderId,
        String restaurantId,
        Instant createdAt,
        OrderApprovalStatus orderApprovalStatus,
        List<String> failureMessages
) {
}

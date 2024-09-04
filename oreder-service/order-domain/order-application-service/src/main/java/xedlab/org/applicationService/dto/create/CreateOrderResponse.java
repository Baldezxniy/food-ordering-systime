package xedlab.org.applicationService.dto.create;

import org.xedlab.commonDomain.valueobject.OrderStatus;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record CreateOrderResponse(
        @NotNull
        UUID orderTrackingId,
        @NotNull
        OrderStatus orderStatus,
        String message
) {
}

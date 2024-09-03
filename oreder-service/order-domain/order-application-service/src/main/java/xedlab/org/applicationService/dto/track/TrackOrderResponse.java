package xedlab.org.applicationService.dto.track;

import org.xedlab.commonDomain.valueobject.OrderStatus;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record TrackOrderResponse(
        @NotNull
        UUID orderTrackingId,
        @NotNull
        OrderStatus status,
        List<String> failureMessages
) {
}

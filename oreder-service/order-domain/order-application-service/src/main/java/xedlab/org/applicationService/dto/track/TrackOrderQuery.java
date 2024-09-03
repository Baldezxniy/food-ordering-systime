package xedlab.org.applicationService.dto.track;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record TrackOrderQuery(
        @NotNull
        UUID orderTrackingId
) {
}

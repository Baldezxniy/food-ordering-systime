package xedlab.org.applicationService.dto.create;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public record OrderAddress(
        @NotNull
        @Max(50)
        String street,
        @NotNull
        @Max(10)
        String postalCode,
        @NotNull
        @Max(50)
        String city
) {
}

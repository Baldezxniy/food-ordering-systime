package org.xedlab.orderService.coreDomain.valueobject;

import org.xedlab.commonDomain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}

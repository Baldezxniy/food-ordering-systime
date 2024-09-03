package xedlab.org.applicationService.ports.in.service;

import xedlab.org.applicationService.dto.create.CreateOrderCommand;
import xedlab.org.applicationService.dto.create.CreateOrderResponse;
import xedlab.org.applicationService.dto.track.TrackOrderQuery;
import xedlab.org.applicationService.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrdeApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}

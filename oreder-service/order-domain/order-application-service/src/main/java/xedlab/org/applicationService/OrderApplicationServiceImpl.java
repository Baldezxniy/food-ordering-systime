package xedlab.org.applicationService;

import org.springframework.stereotype.Service;
import xedlab.org.applicationService.dto.create.CreateOrderCommand;
import xedlab.org.applicationService.dto.create.CreateOrderResponse;
import xedlab.org.applicationService.dto.track.TrackOrderQuery;
import xedlab.org.applicationService.dto.track.TrackOrderResponse;
import xedlab.org.applicationService.ports.in.service.OrdeApplicationService;

@Service
public class OrdeApplicationServiceImpl implements OrdeApplicationService {
    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return null;
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}

package xedlab.org.applicationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xedlab.org.applicationService.dto.create.CreateOrderCommand;
import xedlab.org.applicationService.dto.create.CreateOrderResponse;
import xedlab.org.applicationService.dto.track.TrackOrderQuery;
import xedlab.org.applicationService.dto.track.TrackOrderResponse;

@Slf4j
@Service
class OrderTrackCommandUseCase {

    TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }


}

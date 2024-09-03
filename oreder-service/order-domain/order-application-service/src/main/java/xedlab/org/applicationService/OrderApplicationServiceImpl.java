package xedlab.org.applicationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import xedlab.org.applicationService.dto.create.CreateOrderCommand;
import xedlab.org.applicationService.dto.create.CreateOrderResponse;
import xedlab.org.applicationService.dto.track.TrackOrderQuery;
import xedlab.org.applicationService.dto.track.TrackOrderResponse;
import xedlab.org.applicationService.ports.in.service.OrderApplicationService;

@Slf4j
@Service
@Validated
class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderCreateCommandUseCase orderCreateCommandUseCase;
    private final OrderTrackCommandUseCase orderTrackCommandUseCase;

    OrderApplicationServiceImpl(
            OrderCreateCommandUseCase orderCreateCommandUseCase,
            OrderTrackCommandUseCase orderTrackCommandUseCase
    ) {
        this.orderCreateCommandUseCase = orderCreateCommandUseCase;
        this.orderTrackCommandUseCase = orderTrackCommandUseCase;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandUseCase.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandUseCase.trackOrder(trackOrderQuery);
    }

}

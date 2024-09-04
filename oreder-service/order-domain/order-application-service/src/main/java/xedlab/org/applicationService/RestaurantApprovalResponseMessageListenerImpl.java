package xedlab.org.applicationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xedlab.org.applicationService.dto.message.RestaurantApprovalResponse;
import xedlab.org.applicationService.ports.in.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;

@Slf4j
@Service
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {
    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {

    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {

    }
}

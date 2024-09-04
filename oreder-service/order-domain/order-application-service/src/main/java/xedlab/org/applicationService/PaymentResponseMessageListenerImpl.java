package xedlab.org.applicationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xedlab.org.applicationService.dto.message.PaymentResponse;
import xedlab.org.applicationService.ports.in.message.listener.payment.PaymentResponseMessageListener;

@Slf4j
@Service
class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

    @Override
    public void paymentComplated(PaymentResponse paymentResponse) {

    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {

    }
}

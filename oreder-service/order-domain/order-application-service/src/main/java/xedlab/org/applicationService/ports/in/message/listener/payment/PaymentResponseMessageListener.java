package xedlab.org.applicationService.ports.in.message.listener.payment;

import xedlab.org.applicationService.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentComplated(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);

}

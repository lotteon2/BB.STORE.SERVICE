package kr.bb.store.domain.subscription.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionCreateRequest {
    private Long orderSubscriptionId;
    private Long storeId;
    private Long userId;
    private Long subscriptionProductId;

}

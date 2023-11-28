package kr.bb.store.domain.subscription.controller;

import kr.bb.store.domain.subscription.controller.request.SubscriptionCreateRequest;
import kr.bb.store.domain.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/store-subscriptions")
public class SubscriptionFeignController {
    private final SubscriptionService subscriptionService;
    @PostMapping
    public ResponseEntity createSubscription(@RequestBody SubscriptionCreateRequest subscriptionCreateRequest) {
        subscriptionService.createSubscription(subscriptionCreateRequest);

        return ResponseEntity.ok().build();
    }
}

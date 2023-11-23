package kr.bb.store.domain.store.handler;

import kr.bb.store.domain.store.entity.DeliveryPolicy;
import kr.bb.store.domain.store.entity.Store;
import kr.bb.store.domain.store.entity.StoreAddress;
import kr.bb.store.domain.store.exception.DeliveryPolicyNotFoundException;
import kr.bb.store.domain.store.exception.StoreAddressNotFoundException;
import kr.bb.store.domain.store.exception.StoreNotFoundException;
import kr.bb.store.domain.store.handler.response.*;
import kr.bb.store.domain.store.repository.DeliveryPolicyRepository;
import kr.bb.store.domain.store.repository.StoreAddressRepository;
import kr.bb.store.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class StoreReader {
    private final StoreRepository storeRepository;
    private final StoreAddressRepository storeAddressRepository;
    private final DeliveryPolicyRepository deliveryPolicyRepository;

    private final Float RADIUS_FOR_MAP = 5F;

    public DetailInfoResponse readDetailInfo(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        DeliveryPolicy deliveryPolicy = deliveryPolicyRepository.findByStoreId(storeId)
                .orElseThrow(DeliveryPolicyNotFoundException::new);
        StoreAddress storeAddress = storeAddressRepository.findByStoreId(storeId)
                .orElseThrow(StoreAddressNotFoundException::new);

        return DetailInfoResponse.of(store,deliveryPolicy,storeAddress);
    }

    public Page<Store> readStoresWithPaging(Pageable pageable) {
        return storeRepository.getStoresWithPaging(pageable);
    }

    public StoreInfoUserResponse readForUser(Long storeId, Boolean isLiked, Boolean isSubscribed) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        StoreAddress storeAddress = storeAddressRepository.findByStoreId(storeId)
                .orElseThrow(StoreAddressNotFoundException::new);
        return StoreInfoUserResponse.builder()
                .storeName(store.getStoreName())
                .storeThumbnailImage(store.getStoreThumbnailImage())
                .address(storeAddress.getAddress())
                .averageRating(store.getAverageRating())
                .detailInfo(store.getDetailInfo())
                .phoneNumber(store.getPhoneNumber())
                .isLiked(isLiked)
                .isSubscribed(isSubscribed)
                .build();
    }

    public StoreInfoManagerResponse readForManager(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        StoreAddress storeAddress = storeAddressRepository.findByStoreId(storeId)
                .orElseThrow(StoreAddressNotFoundException::new);
        return StoreInfoManagerResponse.builder()
                .storeName(store.getStoreName())
                .storeThumbnailImage(store.getStoreThumbnailImage())
                .phoneNumber(store.getPhoneNumber())
                .accountNumber(store.getAccountNumber())
                .bank(store.getBank())
                .detailInfo(store.getDetailInfo())
                .address(storeAddress.getAddress())
                .addressDetail(storeAddress.getDetailAddress())
                .build();
    }

    public StoresByLocationResponse getNearbyStores(Float lat, Float lon) {
        List<StoreForMapResponse> nearbyStores = storeRepository.getNearbyStores(lat, lon, RADIUS_FOR_MAP);
        // TODO : 좋아요 여부 feign으로 받아와서 채우기
        return StoresByLocationResponse.builder()
                .stores(nearbyStores)
                .build();
    }
}

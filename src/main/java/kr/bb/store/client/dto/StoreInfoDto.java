package kr.bb.store.client.dto;

import kr.bb.store.domain.store.entity.Store;
import kr.bb.store.domain.store.entity.StoreAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreInfoDto {
    private String storeName;
    private String storeAddress;

    public static StoreInfoDto of(Store store, StoreAddress storeAddress) {
        return StoreInfoDto.builder()
                .storeName(store.getStoreName())
                .storeAddress(storeAddress.getAddress() + " " +storeAddress.getDetailAddress())
                .build();
    }
}
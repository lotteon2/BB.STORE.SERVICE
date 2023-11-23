package kr.bb.store.domain.store.handler.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreForMapResponse {
    private Long storeId;
    private String storeName;
    private Boolean isLiked;
    private String detailInfo;
    private Float averageRating;
    private Float lat;
    private Float lon;

    @QueryProjection
    public StoreForMapResponse(Long storeId, String storeName,String detailInfo, Float averageRating, Float lat, Float lon) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.detailInfo = detailInfo;
        this.averageRating = averageRating;
        this.lat = lat;
        this.lon = lon;
    }
}

package kr.bb.store.domain.coupon.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.bb.store.domain.coupon.dto.*;
import kr.bb.store.domain.coupon.entity.Coupon;

import javax.persistence.EntityManager;
import java.util.List;

import static kr.bb.store.domain.coupon.entity.QCoupon.coupon;
import static kr.bb.store.domain.coupon.entity.QIssuedCoupon.issuedCoupon;

public class CouponRepositoryCustomImpl implements CouponRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public CouponRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CouponForOwnerDto> findAllDtoByStoreId(Long storeId) {
        return queryFactory
                .select(new QCouponForOwnerDto(
                        coupon.store.id,
                        coupon.couponCode,
                        coupon.couponName,
                        coupon.minPrice,
                        coupon.discountPrice,
                        coupon.limitCount.subtract(
                                JPAExpressions
                                        .select(issuedCoupon.count())
                                        .from(issuedCoupon)
                                        .where(issuedCoupon.id.couponId.eq(coupon.id))
                        ),
                        coupon.startDate,
                        coupon.endDate
                ))
                .from(coupon)
                .leftJoin(issuedCoupon)
                .on(coupon.id.eq(issuedCoupon.id.couponId))
                .where(coupon.store.id.eq(storeId))
                .groupBy(
                        coupon.store.id,
                        coupon.couponCode,
                        coupon.couponName,
                        coupon.minPrice,
                        coupon.discountPrice,
                        coupon.limitCount,
                        coupon.startDate,
                        coupon.endDate,
                        coupon.id
                )
                .fetch();
    }

    @Override
    public List<Coupon> findAllValidateCouponsByStoreId(Long storeId) {
        return queryFactory
                .selectFrom(coupon)
                .where(coupon.isDeleted.isFalse())
                .fetch();
    }

    @Override
    public List<CouponWithIssueStatusDto> findStoreCouponsForUser(Long userId, Long storeId) {
        return queryFactory
                .select(new QCouponWithIssueStatusDto(
                    coupon.id,
                    coupon.couponName,
                    coupon.store.storeName,
                    coupon.discountPrice,
                    coupon.endDate,
                    coupon.minPrice,
                    JPAExpressions
                            .select(issuedCoupon.count().gt(0))
                            .from(issuedCoupon)
                            .where(issuedCoupon.id.couponId.eq(coupon.id),
                                    issuedCoupon.id.userId.eq(userId),
                                    issuedCoupon.isUsed.eq(false))
                ))
                .from(coupon)
                .leftJoin(issuedCoupon)
                .on(coupon.id.eq(issuedCoupon.id.couponId))
                .where(coupon.store.id.eq(storeId))
                .fetch();
    }
}

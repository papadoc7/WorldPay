package com.worldpay.merchantoffer.repository;

import com.worldpay.merchantoffer.domain.Offer;
import com.worldpay.merchantoffer.domain.Status;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Offer JPA Repository
 */

@Repository
public interface OfferRepository extends CrudRepository<Offer,Long> {
    List<Offer> findAllByMerchantId(long merchantId);
    List<Offer> findAllByStatus(Status status);
}

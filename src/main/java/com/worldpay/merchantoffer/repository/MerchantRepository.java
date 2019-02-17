package com.worldpay.merchantoffer.repository;

import com.worldpay.merchantoffer.domain.Merchant;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * Merchant JPA Repository
 */
@Repository
public interface MerchantRepository extends CrudRepository<Merchant,Long> {
}

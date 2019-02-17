package com.worldpay.merchantoffer.services;

import com.worldpay.merchantoffer.domain.Offer;
import com.worldpay.merchantoffer.domain.Status;
import com.worldpay.merchantoffer.repository.OfferRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Date;
import java.util.List;

/**
 *
 * Offer Service
 */
@Service
public class OfferService {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(OfferService.class);

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    MerchantService merchantService;

    /**
     * Creates a new offer
     * @param offer
     * @throws ValidationException if expected data is incorrect
     */
    public void add(Offer offer) throws ValidationException {

        offer.setId(0);
        offer.setCreateDateTime(new Date());
        save(offer);
    }

    /**
     * Updates an existing offer.
     * @param offer
     * @throws ValidationException if expected data is incorrect
     */
    public void update(Offer offer) throws ValidationException {
        save(offer);
    }

    private void save(Offer offer) throws ValidationException {
        validate(offer);
        offerRepository.save(offer);
    }

    /**
     * Find offers specific to a merchant
     * @param merchantId
     * @return List<Offer> by id
     */
    public List<Offer> findOffersByMerchant(long merchantId) {
        return offerRepository.findAllByMerchantId(merchantId);
    }

    /**
     * Find offers by specific status
     * @param status
     * @return List<Offer> by status
     */
    public List<Offer> findOffersByStatus(Status status) {
        return offerRepository.findAllByStatus(status);
    }


    /**
     * Find offer
     * @param id unique offer id
     * @return Optional<Offer> or empty if not found
     */
    public Optional<Offer> find(long id) {

        try {
            return Optional.ofNullable(offerRepository.findOne(id).clone());
        } catch (Exception e) {
            logger.error("Offer could not be found for id: " + id);
            return Optional.empty();
        }
    }

    public List<Offer> findAll() {
        List<Offer> offers = new ArrayList<>();
        offerRepository.findAll().forEach(offer -> offers.add(offer.clone()));
        return offers;
    }


    private void validate(Offer offer) throws ValidationException {

        validateOfferExists(offer);
        validateMerchantExists(offer);
        validateCurrency(offer);
        validateStatus(offer);
    }

    /**
     * Ensures that an existing offer is not being updated to EXPIRED status
     * @param offer
     * @throws ValidationException
     */
    protected void validateStatus(Offer offer) throws ValidationException {
        if (offer.getId() > 0) {
            Optional<Offer> existing = find(offer.getId());
            if (existing.isPresent() && existing.get().getStatus() == Status.EXPIRED) {
                throw new ValidationException("Expired offers cannot be updated", offer);
            }
        }
    }

    /**
     * Ensures the currency format is correct
     * @param offer
     * @throws ValidationException
     */
    protected void validateCurrency(Offer offer) throws ValidationException {
        if (offer.getCurrency().length() > 3)
            throw new ValidationException("Currency must be 3 characters long", offer);
    }

    /**
     * Ensures the merchant exists in the system
     * @param offer
     * @throws ValidationException
     */
    protected void validateMerchantExists(Offer offer) throws ValidationException {
        if (!merchantService.find(offer.getMerchantId()).isPresent()) {
            throw new ValidationException("Merchant does not exist", offer);
        }
    }

    /**
     * Ensures the offer exists
     * @param offer
     * @throws ValidationException
     */
    protected void validateOfferExists(Offer offer) throws ValidationException {
        if (offer.getId() > 0) {
            if (!find(offer.getId()).isPresent()) {
                throw new ValidationException("Offer does not exist", offer);
            }
        }
    }

}

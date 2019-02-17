package com.worldpay.merchantoffer.tasks;

import com.worldpay.merchantoffer.domain.Offer;
import com.worldpay.merchantoffer.domain.Status;
import com.worldpay.merchantoffer.services.OfferService;
import com.worldpay.merchantoffer.services.ValidationException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * Offer expiration after their time to live, runs scheduled every 30 seconds
 *
 */
@Component
public class OfferExpirationTask {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(OfferExpirationTask.class);

    @Autowired
    OfferService offerService;


    @Scheduled(fixedRate = 30000) //runs every 30seconds
    public void expireTasks() {

        offerService.findOffersByStatus(Status.ACTIVE)
                .stream()
                .filter(this::isExpiring)
                .forEach(this::expireOffer);
    }

    /**
     * Updates an offer to EXPIRED status
     * @param offer
     */
    protected void expireOffer(Offer offer) {
        try {
            offer.setStatus(Status.EXPIRED);
            offerService.update(offer);

            logger.info("Expiring offer " + offer.getDescription());
        } catch (ValidationException e) {
            logger.error("failed to expire offer",e);
        }
    }

    /**
     * Checks if the Offer has exceeded its life time
     * @param offer
     */
    protected boolean isExpiring(Offer offer) {
        Date createDateTime = offer.getCreateDateTime();
        LocalDateTime localCreateDateTime = LocalDateTime.ofInstant(createDateTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime currentDateTime = LocalDateTime.now();

        long elapsedMinutes = Duration.between(localCreateDateTime, currentDateTime).toMinutes();
        return elapsedMinutes >= offer.getTimeToLiveMinutes() ;
    }

}

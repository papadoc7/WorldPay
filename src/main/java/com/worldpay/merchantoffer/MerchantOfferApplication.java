package com.worldpay.merchantoffer;

import com.worldpay.merchantoffer.domain.Merchant;
import com.worldpay.merchantoffer.domain.Offer;
import com.worldpay.merchantoffer.domain.Status;
import com.worldpay.merchantoffer.services.MerchantService;
import com.worldpay.merchantoffer.services.OfferService;
import com.worldpay.merchantoffer.services.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
@EnableScheduling
public class MerchantOfferApplication implements ApplicationListener<ApplicationEvent> {
    @Autowired
    MerchantService merchantService;

    @Autowired
    OfferService offerService;

    public static void main(String[] args) {
        SpringApplication.run(MerchantOfferApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent.getSource() instanceof EmbeddedServletContainer) {
            setupTestData();
        }
    }

    /**
     * Creates test data for the purpose of this assignment to illustrate functionality
     */
    private void setupTestData() {
        Merchant merchant = new Merchant();
        merchant.setName("Georgios");

        merchantService.save(merchant);

        Optional<Merchant> newMerchant = merchantService.find(merchant.getId());

        Offer offer = new Offer();
        offer.setCurrency("GBP");
        offer.setDescription("Offer description");
        offer.setMerchantId(merchant.getId());
        offer.setPrice(BigDecimal.valueOf(330.15));
        offer.setStatus(Status.ACTIVE);
        offer.setTimeToLiveMinutes(15);
        offer.setCreateDateTime(new Date());
        try {
            offerService.add(offer);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }

}

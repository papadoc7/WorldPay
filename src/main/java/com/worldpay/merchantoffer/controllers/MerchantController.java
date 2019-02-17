package com.worldpay.merchantoffer.controllers;

import com.worldpay.merchantoffer.domain.Merchant;
import com.worldpay.merchantoffer.services.MerchantService;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * REST Controller for Merchants
 */
@RestController
@RequestMapping(value = "merchants", produces = "application/json")
@Api(tags = "merchants")
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    @GetMapping
    public List<Merchant> getMerchants() {
        return merchantService.findAll();
    }

}

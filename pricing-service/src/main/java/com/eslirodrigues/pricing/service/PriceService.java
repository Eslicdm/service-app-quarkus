package com.eslirodrigues.pricing.service;

import com.eslirodrigues.pricing.dto.PriceUpdateDTO;
import com.eslirodrigues.pricing.entity.Price;
import com.eslirodrigues.pricing.entity.PriceType;
import com.eslirodrigues.pricing.messaging.PriceEventEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PriceService {

    private final PriceEventEmitter priceEventEmitter;

    @Inject
    public PriceService(PriceEventEmitter priceEventEmitter) {
        this.priceEventEmitter = priceEventEmitter;
    }

    public List<Price> getAllPrices() {
        return Price.listAll();
    }

    @Transactional
    public Price updatePrice(PriceType priceType, PriceUpdateDTO priceUpdateDTO) {
        Price price = Price.findByPriceType(priceType).orElseGet(() -> {
            Price newPrice = new Price();
            newPrice.priceType = priceType;
            newPrice.createdAt = LocalDateTime.now();
            return newPrice;
        });

        price.value = priceUpdateDTO.value();
        price.description = priceUpdateDTO.description();
        price.updatedAt = LocalDateTime.now();

        price.persistOrUpdate();

        priceEventEmitter.sendPriceUpdated(price);

        return price;
    }
}
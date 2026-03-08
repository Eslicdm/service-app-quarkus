package com.eslirodrigues.pricing.converter;

import com.eslirodrigues.pricing.entity.PriceType;
import jakarta.ws.rs.BadRequestException;

public class PriceTypeConverter {

    private PriceTypeConverter() {}

    public static PriceType convert(String value) {
        try {
            return PriceType.fromValue(value);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid price type: '" + value + "'");
        }
    }
}

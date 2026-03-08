package com.eslirodrigues.pricing.controller;

import com.eslirodrigues.pricing.converter.PriceTypeConverter;
import com.eslirodrigues.pricing.dto.PriceUpdateDTO;
import com.eslirodrigues.pricing.entity.Price;
import com.eslirodrigues.pricing.entity.PriceType;
import com.eslirodrigues.pricing.service.PriceService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/prices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class PriceController {

    @Inject
    PriceService priceService;

    @GET
    public Response getAllPrices() {
        List<Price> prices = priceService.getAllPrices();
        return Response.ok(prices).build();
    }

    @PUT
    @Path("/{priceType}")
    @RolesAllowed({"manager", "admin"})
    public Response updatePrice(
            @PathParam("priceType") String priceTypeStr,
            @Valid PriceUpdateDTO priceUpdateDTO
    ) {
        PriceType priceType = PriceTypeConverter.convert(priceTypeStr);
        Price updated = priceService.updatePrice(priceType, priceUpdateDTO);
        return Response.ok(updated).build();
    }
}

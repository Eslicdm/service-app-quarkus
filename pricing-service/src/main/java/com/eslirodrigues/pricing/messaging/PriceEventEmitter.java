package com.eslirodrigues.pricing.messaging;

import com.eslirodrigues.pricing.entity.Price;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;

@ApplicationScoped
public class PriceEventEmitter {

    private final Emitter<Price> priceUpdatedEmitter;

    public PriceEventEmitter(
            @Channel("price-updated-out")
            Emitter<Price> priceUpdatedEmitter
    ) {
        this.priceUpdatedEmitter = priceUpdatedEmitter;
    }

    public void sendPriceUpdated(Price price) {
        OutgoingRabbitMQMetadata metadata = new OutgoingRabbitMQMetadata.Builder()
                .withRoutingKey("price.updated.key")
                .build();

        priceUpdatedEmitter.send(
                Message.of(price, Metadata.of(metadata))
        );
    }
}

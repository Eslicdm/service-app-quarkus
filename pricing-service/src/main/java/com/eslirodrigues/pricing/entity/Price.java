package com.eslirodrigues.pricing.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@MongoEntity(collection = "prices")
public class Price extends PanacheMongoEntity {

    public PriceType priceType;
    public BigDecimal value;
    public String description;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public Price() {
    }

    public Price(
            PriceType priceType,
            BigDecimal value,
            String description,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.priceType = priceType;
        this.value = value;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Optional<Price> findByPriceType(PriceType priceType) {
        return find("priceType", priceType).firstResultOptional();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(id, price.id) &&
                priceType == price.priceType &&
                Objects.equals(value, price.value) &&
                Objects.equals(description, price.description) &&
                Objects.equals(createdAt, price.createdAt) &&
                Objects.equals(updatedAt, price.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priceType, value, description, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Price{" +
                "id='" + id + '\'' +
                ", priceType=" + priceType +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
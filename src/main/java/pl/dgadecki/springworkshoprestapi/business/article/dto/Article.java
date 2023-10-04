package pl.dgadecki.springworkshoprestapi.business.article.dto;

import java.math.BigDecimal;

public record Article(
        Long id,
        String name,
        String description,
        String producer,
        String eanCode,
        BigDecimal price
) { }

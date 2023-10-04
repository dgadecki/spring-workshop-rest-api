package pl.dgadecki.springworkshoprestapi.business.article.dto.api;

import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;

import java.math.BigDecimal;

public record UpdateArticleResponse(
        Long id,
        String name,
        String description,
        String producer,
        String eanCode,
        BigDecimal price
) {

    public static UpdateArticleResponse from(Article article) {
        return new UpdateArticleResponse(
                article.id(),
                article.name(),
                article.description(),
                article.producer(),
                article.eanCode(),
                article.price()
        );
    }
}

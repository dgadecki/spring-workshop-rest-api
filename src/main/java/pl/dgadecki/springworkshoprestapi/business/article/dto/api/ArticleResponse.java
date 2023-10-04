package pl.dgadecki.springworkshoprestapi.business.article.dto.api;

import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;

import java.math.BigDecimal;

public record ArticleResponse(
        Long id,
        String name,
        String description,
        String producer,
        String eanCode,
        BigDecimal price
) {

    public static ArticleResponse fromArticle(Article article) {
        return new ArticleResponse(
                article.id(),
                article.name(),
                article.description(),
                article.producer(),
                article.eanCode(),
                article.price()
        );
    }
}

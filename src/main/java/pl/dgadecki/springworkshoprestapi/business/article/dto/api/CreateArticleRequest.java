package pl.dgadecki.springworkshoprestapi.business.article.dto.api;

import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;

import java.math.BigDecimal;

public record CreateArticleRequest(
        String name,
        String description,
        String producer,
        String eanCode,
        BigDecimal price
) {

    public Article toArticle() {
        return new Article(null, name, description, producer, eanCode, price);
    }
}

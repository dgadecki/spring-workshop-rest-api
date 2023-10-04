package pl.dgadecki.springworkshoprestapi.business.article.dto.api;

import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;

import java.math.BigDecimal;

public record UpdateArticleRequest(
        Long id,
        String name,
        String description,
        String producer,
        String eanCode,
        BigDecimal price
) {

    public Article toArticle() {
        return new Article(id, name, description, producer, eanCode, price);
    }
}

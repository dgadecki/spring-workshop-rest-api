package pl.dgadecki.springworkshoprestapi.business.article.dto.api;

import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;

import java.util.List;

public record FindAllArticlesResponse(
        List<ArticleResponse> articles
) {

    public static FindAllArticlesResponse from(List<Article> articles) {
        List<ArticleResponse> articleResponses = articles.stream()
                .map(ArticleResponse::fromArticle)
                .toList();

        return new FindAllArticlesResponse(articleResponses);
    }
}

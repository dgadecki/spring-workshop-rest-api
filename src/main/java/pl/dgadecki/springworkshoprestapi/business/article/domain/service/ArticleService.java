package pl.dgadecki.springworkshoprestapi.business.article.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dgadecki.springworkshoprestapi.business.article.domain.repository.ArticleRepository;
import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    public Article fetchArticleById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("Article with id " + articleId + " does not exist"));
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public Article updateArticle(Long articleId, Article article) {
        if (!articleId.equals(article.id())) {
            throw new IllegalArgumentException("Article id from path variable " + articleId + " does not match article id from Article to update " + article.id());
        }
        return articleRepository.update(article);
    }

    public void deleteArticle(Long articleId) {
        articleRepository.delete(articleId);
    }
}

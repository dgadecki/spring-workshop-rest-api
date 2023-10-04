package pl.dgadecki.springworkshoprestapi.business.article.domain.repository;

import org.springframework.stereotype.Repository;
import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ArticleRepository {

    private final Map<Long, Article> articles = new ConcurrentHashMap<>();

    public Article save(Article article) {
        Long articleId = article.id() != null ? article.id() : new Random().nextLong();
        Article articleToSave = new Article(articleId, article.name(), article.description(), article.producer(), article.eanCode(), article.price());
        articles.put(articleId, articleToSave);
        return articles.get(articleId);
    }

    public Article update(Article article) {
        if (articles.containsKey(article.id())) {
            articles.put(article.id(), article);
            return articles.get(article.id());
        } else {
            throw new IllegalArgumentException("Article with id " + article.id() + " does not exist");
        }
    }

    public void delete(Long articleId) {
        if (articles.containsKey(articleId)) {
            articles.remove(articleId);
        } else {
            throw new IllegalArgumentException("Article with id " + articleId + " does not exist");
        }
    }

    public Optional<Article> findById(Long articleId) {
        return Optional.ofNullable(articles.get(articleId));
    }

    public List<Article> findAll() {
        return new ArrayList<>(articles.values());
    }
}

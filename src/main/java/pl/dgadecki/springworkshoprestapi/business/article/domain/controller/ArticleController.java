package pl.dgadecki.springworkshoprestapi.business.article.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.dgadecki.springworkshoprestapi.business.article.domain.service.ArticleService;
import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;
import pl.dgadecki.springworkshoprestapi.business.article.dto.api.ArticleResponse;
import pl.dgadecki.springworkshoprestapi.business.article.dto.api.CreateArticleRequest;
import pl.dgadecki.springworkshoprestapi.business.article.dto.api.CreateArticleResponse;
import pl.dgadecki.springworkshoprestapi.business.article.dto.api.FindAllArticlesResponse;
import pl.dgadecki.springworkshoprestapi.business.article.dto.api.UpdateArticleRequest;
import pl.dgadecki.springworkshoprestapi.business.article.dto.api.UpdateArticleResponse;
import pl.dgadecki.springworkshoprestapi.common.error.ErrorResponse;

@Tag(name = "Article API", description = "All operations for articles")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "The articles were successfully found", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FindAllArticlesResponse.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error - unexpected error", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))
        })
})
@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "Find all articles", description = "Returns all articles available in the system")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FindAllArticlesResponse findAllArticles() {
        return FindAllArticlesResponse.from(articleService.findAllArticles());
    }

    @Operation(summary = "Find article by id", description = "Returns article with given id if exists or error otherwise")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArticleResponse fetchArticleById(@Parameter(description = "Identifier of the article that should be found") @PathVariable("id") Long articleId) {
        return ArticleResponse.fromArticle(articleService.fetchArticleById(articleId));
    }

    @Operation(summary = "Create article", description = "Creates article based on the data passed in request")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateArticleResponse createArticle(@Parameter(description = "Request body to create articles data") @RequestBody CreateArticleRequest createArticleRequest) {
        Article createdArticle = articleService.saveArticle(createArticleRequest.toArticle());
        return CreateArticleResponse.from(createdArticle);
    }

    @Operation(summary = "Update article", description = "Updates article with given id based on the data passed in request")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdateArticleResponse updateArticle(@Parameter(description = "Identifier of the article that should be updated") @PathVariable("id") Long articleId,
                                               @Parameter(description = "Request body to update articles data") @RequestBody UpdateArticleRequest updateArticleRequest) {
        Article updatedArticle = articleService.updateArticle(articleId, updateArticleRequest.toArticle());
        return UpdateArticleResponse.from(updatedArticle);
    }

    @Operation(summary = "Delete article", description = "Deletes article with given id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteArticle(@Parameter(description = "Identifier of the article that should be deleted") @PathVariable("id") Long articleId) {
        articleService.deleteArticle(articleId);
    }
}

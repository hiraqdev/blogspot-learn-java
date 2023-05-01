package blogspot.core.article;

import blogspot.core.blog.BlogUID;

import java.time.Instant;
import java.util.Objects;

public record ArticleEntity(ArticleUID uid, BlogUID blogUID, String title, String desc, Instant createdAt, Instant updatedAt, Instant deletedA) {
    public ArticleEntity {
        Objects.requireNonNull(title);
        Objects.requireNonNull(desc);
    }

    public ArticleEntity(BlogUID blogUID, String title, String desc) {
        this(new ArticleUID(), blogUID, title, desc, Instant.now(), Instant.now(), null);
    }
}

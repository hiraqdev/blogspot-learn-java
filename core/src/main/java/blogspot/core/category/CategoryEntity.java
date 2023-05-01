package blogspot.core.category;

import blogspot.core.blog.BlogUID;

import java.time.Instant;
import java.util.Objects;

public record CategoryEntity(CategoryUID uid, BlogUID blogUID, String name, Instant createdAt, Instant updatedAt, Instant deletedA) {
    public CategoryEntity {
        Objects.requireNonNull(name);
    }

    public CategoryEntity(BlogUID blogUID, String name) {
        this(new CategoryUID(), blogUID, name, Instant.now(), Instant.now(), Instant.now());
    }
}

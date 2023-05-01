package blogspot.core.article;

import java.util.UUID;

public record ArticleUID(UUID uid) {
    public ArticleUID() {
        this(UUID.randomUUID());
    }
}

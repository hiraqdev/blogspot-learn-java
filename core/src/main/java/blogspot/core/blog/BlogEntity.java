package blogspot.core.blog;

import blogspot.core.user.UserUID;

import java.time.Instant;
import java.util.Objects;

public record BlogEntity(BlogUID uid, UserUID userUID, String name, String desc, Boolean isBanned, Instant createdAt, Instant updatedAt, Instant deletedAt) {
    public BlogEntity {
        Objects.requireNonNull(name);
        Objects.requireNonNull(desc);
    }

    public BlogEntity(UserUID userUID, String name, String desc) {
        this(new BlogUID(), userUID, name, desc, false, Instant.now(), Instant.now(), null);
    }
}

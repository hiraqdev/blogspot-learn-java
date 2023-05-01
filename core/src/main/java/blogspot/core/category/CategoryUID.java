package blogspot.core.category;

import java.util.UUID;

public record CategoryUID(UUID uid) {
    public CategoryUID() {
        this(UUID.randomUUID());
    }
}

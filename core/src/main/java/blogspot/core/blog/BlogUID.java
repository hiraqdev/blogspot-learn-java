package blogspot.core.blog;

import java.util.UUID;

public record BlogUID(UUID uid) {

    public BlogUID() {
        this(UUID.randomUUID());
    }

}

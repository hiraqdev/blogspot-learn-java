package blogspot.core.user;

import java.util.UUID;

public record UserUID(UUID uid) {
    public UserUID() {
        this(UUID.randomUUID());
    }
}

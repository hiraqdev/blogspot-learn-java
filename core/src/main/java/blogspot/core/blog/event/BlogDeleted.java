package blogspot.core.blog.event;

import blogspot.core.blog.BlogUID;
import blogspot.core.common.event.DomainEvent;
import blogspot.core.user.UserUID;

public record BlogDeleted(BlogUID uid, UserUID userUID, String name) implements DomainEvent {
    public final static String EVENT_NAME = "BlogDeleted";

    public String name() {
        return EVENT_NAME;
    }
}

package blogspot.core.blog.event;

import blogspot.core.blog.BlogUID;
import blogspot.core.common.event.DomainEvent;
import blogspot.core.user.UserUID;

public record BlogCreated(BlogUID uid, UserUID userUID, String name, String desc) implements DomainEvent {
    public final static String EVENT_NAME = "BlogCreated";

    public String name() {
        return EVENT_NAME;
    }
}

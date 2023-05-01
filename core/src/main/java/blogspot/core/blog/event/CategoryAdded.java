package blogspot.core.blog.event;

import blogspot.core.blog.BlogUID;
import blogspot.core.category.CategoryUID;
import blogspot.core.common.event.DomainEvent;

public record CategoryAdded(CategoryUID categoryUID, BlogUID blogUID, String name) implements DomainEvent {
    public final static String EVENT_NAME = "CategoryAdded";

    public String name() {
        return EVENT_NAME;
    }
}

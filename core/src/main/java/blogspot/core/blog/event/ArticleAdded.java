package blogspot.core.blog.event;

import blogspot.core.article.ArticleUID;
import blogspot.core.blog.BlogUID;
import blogspot.core.common.event.DomainEvent;

public record ArticleAdded(ArticleUID articleUID, BlogUID blogUID, String title, String desc) implements DomainEvent {
    public final static String EVENT_NAME = "ArticleAdded";

    public String name() {
        return EVENT_NAME;
    }
}

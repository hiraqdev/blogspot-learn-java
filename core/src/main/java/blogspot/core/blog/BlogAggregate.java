package blogspot.core.blog;

import blogspot.core.blog.event.*;
import blogspot.core.common.event.Publisher;
import blogspot.core.article.ArticleEntity;
import blogspot.core.category.CategoryEntity;
import blogspot.core.user.UserUID;

import java.time.Instant;

/**
 * BlogAggregate is a domain aggregate specific only for Blog domain
 * By using this aggregate class we should be able to
 * - load aggregate from repository
 * - create new blog
 * - add new article to a blog
 * - add new category to a blog
 */
public final class BlogAggregate extends Publisher {

    public BlogEntity entity;

    public static class Load {

        private final BlogUID blogUID;
        private final UserUID userUID;
        private final String name;
        private final String desc;
        private final Boolean isBanned;
        private final Instant createdAt;
        private final Instant updatedAt;
        private final Instant deletedAt;

        public Load(BlogUID blogUID, UserUID userUID, String name, String desc, Boolean isBanned, Instant createdAt, Instant updatedAt, Instant deletedAt) {
            this.blogUID = blogUID;
            this.userUID = userUID;
            this.name = name;
            this.desc = desc;
            this.isBanned = isBanned;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.deletedAt = deletedAt;
        }

        public BlogAggregate build() {
            return new BlogAggregate(this);
        }
    }

    public static class New {
        private final UserUID userUID;

        private final String name;
        private final String desc;

        public New(UserUID userUID, String name, String desc) {
            this.userUID = userUID;
            this.name = name;
            this.desc = desc;
        }

        public BlogAggregate build() {
            return new BlogAggregate(this);
        }
    }

    private BlogAggregate(Load builder) {
        this.entity = new BlogEntity(
                builder.blogUID,
                builder.userUID,
                builder.name,
                builder.desc,
                builder.isBanned,
                builder.createdAt,
                builder.updatedAt,
                builder.deletedAt
        );
    }

    private BlogAggregate(New data) {
        this.entity = new BlogEntity(data.userUID, data.name, data.desc);
    }

    /**
     * This method used only when we need to trigger an event BlogCreated.  It's better to trigger this method
     * from a repository after its success to added this current blog
     */
    public void triggerBlogCreated() {
        this.raiseEvent(new BlogCreated(this.entity.uid(), this.entity.userUID(), this.entity.name(), this.entity.desc()));
    }

    /**
     * This method used only when we need to trigger an event BlogDeleted.  It's better to trigger this method
     * from a repository after its success to delete current blog
     */
    public void triggerBlogDeleted() {
        this.raiseEvent(new BlogDeleted(this.entity.uid(), this.entity.userUID(), this.entity.name()));
    }

    /**
     * This method used to update current blog's name and description, after update completed, it will
     * raise an event BlogEdited
     *
     * @param name A new blog's name
     * @param desc A new blog's desc
     */
    public void updateBlogNameDesc(String name, String desc) {
        this.entity = new BlogEntity(
                this.entity.uid(),
                this.entity.userUID(),
                name,
                desc,
                false,
                this.entity.createdAt(),
                Instant.now(),
                this.entity.deletedAt()
        );

        this.raiseEvent(new BlogEdited(this.entity.uid(), this.entity.userUID(), name));
    }

    /**
     * This method must be called by an admin only, used to ban current blog
     */
    public void ban() {
        this.entity = new BlogEntity(
                this.entity.uid(),
                this.entity.userUID(),
                this.entity.name(),
                this.entity.desc(),
                true,
                this.entity.createdAt(),
                Instant.now(),
                this.entity.deletedAt()
        );

        this.raiseEvent(new BlogBanned(this.entity.uid(), this.entity.userUID(), this.entity.name()));
    }

    /**
     * This method will not be used to direct create an article.  Build article's entity and raise an event
     * ArticleAdded in memory.  An article's persistent should be handled by external aggregate, which is
     * ArticleAggregate.
     *
     * @param title The article's title or subject
     * @param desc The article's description or body
     */
    public void addArticle(String title, String desc) {
        ArticleEntity article = new ArticleEntity(this.entity.uid(), title, desc);
        this.raiseEvent(new ArticleAdded(article.uid(), article.blogUID(), article.title(), article.desc()));
    }

    /**
     * This method will not be used to direct create an article.  Build article's entity and raise an event
     * CategoryAdded in memory.  An article's persistent should be handled by external aggregate, which is
     * CategoryAggregate.
     *
     * @param name A category name that want to be added to current blog
     */
    public void addCategory(String name) {
        CategoryEntity category = new CategoryEntity(this.entity.uid(), name);
        this.raiseEvent(new CategoryAdded(category.uid(), this.entity.uid(), name));
    }
}

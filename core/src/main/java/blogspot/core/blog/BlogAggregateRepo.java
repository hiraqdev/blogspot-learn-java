package blogspot.core.blog;

public interface BlogAggregateRepo {
    void save(BlogAggregate blog);

    void create(BlogAggregate blog);

    BlogAggregate findByUID(BlogUID uid);
}

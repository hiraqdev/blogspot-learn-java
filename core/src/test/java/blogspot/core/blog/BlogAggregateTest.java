package blogspot.core.blog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import blogspot.core.common.event.DomainEvent;
import blogspot.core.common.event.Listener;
import blogspot.core.user.UserUID;
import blogspot.core.blog.event.*;

import java.time.Instant;

import static org.mockito.Mockito.*;

public class BlogAggregateTest {

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test void testNew() {
        UserUID userUID = new UserUID();
        BlogAggregate blog = new BlogAggregate.New(userUID, "title", "desc").build();
        assertNotNull(blog.entity.uid());
        assertEquals(userUID, blog.entity.userUID());
        assertEquals("title", blog.entity.name());
        assertEquals("desc", blog.entity.desc());
        assertFalse(blog.entity.isBanned());
        assertNotNull(blog.entity.createdAt());
        assertNotNull(blog.entity.updatedAt());
        assertNull(blog.entity.deletedAt());
    }

    @Test void testNewNullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> new BlogAggregate.New(new UserUID(), null, null).build());
    }

    @Test void testLoader() {
        BlogUID blogUID = new BlogUID();
        UserUID userUID = new UserUID();
        BlogAggregate blog = new BlogAggregate.Load(
                blogUID,
                userUID,
                "title",
                "desc",
                false,
                Instant.now(),
                Instant.now(),
                null).build();

        assertEquals(blogUID, blog.entity.uid());
        assertEquals(userUID, blog.entity.userUID());
        assertEquals("title", blog.entity.name());
        assertEquals("desc", blog.entity.desc());
        assertFalse(blog.entity.isBanned());
        assertNotNull(blog.entity.createdAt());
        assertNotNull(blog.entity.updatedAt());
        assertNull(blog.entity.deletedAt());
    }

    @Test void testLoaderNullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> {
            BlogUID blogUID = new BlogUID();
            UserUID userUID = new UserUID();
            new BlogAggregate.Load(
                    blogUID,
                    userUID,
                    null,
                    null,
                    false,
                    Instant.now(),
                    Instant.now(),
                    null).build();
        });
    }

    @Test void testListenerTriggerBlogCreated() {
        Listener listener = mock(Listener.class);
        UserUID userUID = new UserUID();

        BlogAggregate blog = new BlogAggregate.New(userUID, "title", "desc").build();
        blog.registerListener(listener, BlogCreated.EVENT_NAME);
        blog.triggerBlogCreated();
        verify(listener).on(any(DomainEvent.class));
    }

    @Test void testListenerTriggerBlogDeleted() {
        Listener listener = mock(Listener.class);
        UserUID userUID = new UserUID();

        BlogAggregate blog = new BlogAggregate.New(userUID, "title", "desc").build();
        blog.registerListener(listener, BlogDeleted.EVENT_NAME);
        blog.triggerBlogDeleted();
        verify(listener).on(any(DomainEvent.class));
    }

    @Test void testUpdateBlogNameDesc() {
        Listener listener = mock(Listener.class);
        UserUID userUID = new UserUID();

        BlogAggregate blog = new BlogAggregate.New(userUID, "title", "desc").build();
        blog.registerListener(listener, BlogEdited.EVENT_NAME);
        blog.updateBlogNameDesc("title2", "desc2");

        assertEquals("title2", blog.entity.name());
        assertEquals("desc2", blog.entity.desc());
        verify(listener).on(any(DomainEvent.class));
    }

    @Test void testBan() {
        Listener listener = mock(Listener.class);
        UserUID userUID = new UserUID();

        BlogAggregate blog = new BlogAggregate.New(userUID, "title", "desc").build();
        blog.registerListener(listener, BlogBanned.EVENT_NAME);
        blog.ban();

        assertEquals(true, blog.entity.isBanned());
        verify(listener).on(any(DomainEvent.class));
    }

    @Test void testAddArticle() {
        Listener listener = mock(Listener.class);
        UserUID userUID = new UserUID();

        BlogAggregate blog = new BlogAggregate.New(userUID, "title", "desc").build();
        blog.registerListener(listener, ArticleAdded.EVENT_NAME);
        blog.addArticle("article-title", "article-desc");
        verify(listener).on(any(DomainEvent.class));
    }

    @Test void testAddCategory() {
        Listener listener = mock(Listener.class);
        UserUID userUID = new UserUID();

        BlogAggregate blog = new BlogAggregate.New(userUID, "title", "desc").build();
        blog.registerListener(listener, CategoryAdded.EVENT_NAME);
        blog.addCategory("category");
        verify(listener).on(any(DomainEvent.class));
    }

    @Test void testListenerNeverCalled() {
        Listener listener = mock(Listener.class);
        Listener listener2 = mock(Listener.class);
        UserUID userUID = new UserUID();

        BlogAggregate blog = new BlogAggregate.New(userUID, "title", "desc").build();
        blog.registerListener(listener, BlogCreated.EVENT_NAME);
        blog.registerListener(listener2, BlogEdited.EVENT_NAME);
        blog.triggerBlogCreated();

        verify(listener).on(any(DomainEvent.class));
        verify(listener2, never()).on(any(DomainEvent.class));
    }
}

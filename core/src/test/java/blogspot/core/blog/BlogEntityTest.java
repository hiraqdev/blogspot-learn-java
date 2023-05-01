package blogspot.core.blog;

import blogspot.core.user.UserUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlogEntityTest {
   @Test void testConstructor() {
       UserUID userUID = new UserUID();
       BlogEntity entity = new BlogEntity(userUID, "title", "desc");
       assertNotNull(entity.uid());
       assertEquals(userUID, entity.userUID());
       assertEquals("title", entity.name());
       assertEquals("desc", entity.desc());
       assertNotNull(entity.createdAt());
       assertNotNull(entity.updatedAt());
       assertNull(entity.deletedAt());
   }

   @Test void testNullExceptionsTitleAndDesc() {
       assertThrows(NullPointerException.class, () -> new BlogEntity(new UserUID(), null, null));
   }
}

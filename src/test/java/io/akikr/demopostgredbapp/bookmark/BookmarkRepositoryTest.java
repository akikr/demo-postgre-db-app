package io.akikr.demopostgredbapp.bookmark;

import io.akikr.demopostgredbapp.PostgreTestContainer;
import org.instancio.Instancio;
import org.instancio.generators.Generators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.instancio.Select.field;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@JdbcTest
class BookmarkRepositoryTest extends PostgreTestContainer {

    @Autowired
    private JdbcClient jdbcClient;

    private BookmarkRepository bookmarkRepository;

    @BeforeEach
    void setUp() {
        bookmarkRepository = new BookmarkRepository(jdbcClient);
        System.out.println("BookmarkRepository initialized for testing");
    }

    @Test
    @Sql(scripts = "classpath:test-data.sql", executionPhase = BEFORE_TEST_METHOD)
    void shouldFindAllBookmarks() {
        // Arrange
        // Assuming test-data.sql has been executed to insert test data
        int pageNumber = 0, pageSize = 6; // Adjust page size as per your test data

        // Act
        var bookmarks = bookmarkRepository.findAll(pageNumber, pageSize);

        // Assert
        assertThat(bookmarks).isNotNull();
        assertThat(bookmarks).isNotEmpty();
        assertThat(bookmarks).hasSize(6); // Assuming 6 bookmarks are inserted in test-data.sql
    }

    @Test
    void shouldCreateBookmark() {
        // Arrange
        var newBookmark = new Bookmark(null, "New Bookmark", "http://newbookmark.com", LocalDateTime.now());

        // Act
        var createdBookmarkId = bookmarkRepository.save(newBookmark);

        // Assert
        assertThat(createdBookmarkId).isNotNull();
    }

    @Test
    @Sql(scripts = "classpath:test-data.sql", executionPhase = BEFORE_TEST_METHOD)
    void shouldGetBookmarkById() {
        // Arrange
        Bookmark bookmarkData = Instancio.of(Bookmark.class)
                .generate(field("title"), gen -> gen.string().length(10))
                .generate(field("url"), gen -> gen.string().prefix("https://").suffix(".com").lowerCase().length(10))
                .generate(field("createdAt"), gen -> gen.temporal().localDateTime().range(LocalDateTime.now(), LocalDateTime.now().plusSeconds(1)))
                .create();
        Long bookmarkId = bookmarkRepository.save(bookmarkData);

        // Act
        var bookmark = bookmarkRepository.findById(bookmarkId);

        // Assert
        assertThat(bookmark).isNotNull();
        assertThat(bookmark).isPresent();
        assertThat(bookmark.get().id()).isEqualTo(bookmarkId);
    }

    @Test
    void shouldBeEmptyWhenBookmarkNotFound() {
        // Arrange
        Long nonExistentBookmarkId = 999L; // Assuming this ID does not exist

        // Act
        var bookmark = bookmarkRepository.findById(nonExistentBookmarkId);

        // Assert
        assertThat(bookmark).isNotNull();
        assertThat(bookmark).isEmpty();
    }

    @Test
    void shouldUpdateBookmark() {
        // Arrange
        Bookmark bookmarkData = Instancio.of(Bookmark.class)
                .generate(field("title"), Generators::string)
                .generate(field("url"), gen -> gen.string().prefix("https://").suffix(".com").lowerCase().length(10))
                .generate(field("createdAt"), gen -> gen.temporal().localDateTime().range(LocalDateTime.now(), LocalDateTime.now().plusSeconds(1)))
                .create();
        Long bookmarkId = bookmarkRepository.save(bookmarkData);

        // Act
        bookmarkData = new Bookmark(bookmarkId, "Updated Title", "https://updatedurl.com", LocalDateTime.now());
        Boolean isUpdated = bookmarkRepository.update(bookmarkData);

        // Assert
        assertThat(isUpdated).isNotNull();
        assertThat(isUpdated).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentBookmark() {
        // Arrange
        Long nonExistentBookmarkId = 999L; // Assuming this ID does not exist
        Bookmark bookmarkData = new Bookmark(nonExistentBookmarkId, "Updated Title", "https://updatedurl.com", LocalDateTime.now());

        // Act & Assert
        assertThatThrownBy(() -> bookmarkRepository.update(bookmarkData))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Bookmark NOT found with id: " + nonExistentBookmarkId);
    }

    @Test
    void shouldDeleteBookmarkById() {
        // Arrange
        Bookmark bookmarkData = Instancio.of(Bookmark.class)
                .generate(field("title"), Generators::string)
                .generate(field("url"), gen -> gen.string().prefix("https://").suffix(".com").lowerCase().length(10))
                .generate(field("createdAt"), gen -> gen.temporal().localDateTime().range(LocalDateTime.now(), LocalDateTime.now().plusSeconds(1)))
                .create();
        Long bookmarkId = bookmarkRepository.save(bookmarkData);

        // Act
        Boolean isDeleted = bookmarkRepository.deleteById(bookmarkId);

        // Assert
        assertThat(isDeleted).isNotNull();
        assertThat(isDeleted).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentBookmark() {
        // Arrange
        Long nonExistentBookmarkId = 999L; // Assuming this ID does not exist

        // Act & Assert
        assertThatThrownBy(() -> bookmarkRepository.deleteById(nonExistentBookmarkId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Bookmark NOT found with id: " + nonExistentBookmarkId);
    }
}

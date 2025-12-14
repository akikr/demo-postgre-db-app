package io.akikr.demopostgredbapp.bookmark;

import io.akikr.demopostgredbapp.PostgreTestContainer;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@JdbcTest
class BookmarkServiceTest extends PostgreTestContainer {

    @Autowired
    private JdbcClient jdbcClient;

    private BookmarkRepository bookmarkRepository;
    private  BookmarkService bookmarkService;

    @BeforeEach
    void setUp() {
        bookmarkRepository = new BookmarkRepository(jdbcClient);
        bookmarkService = new BookmarkServiceImpl(bookmarkRepository);
        System.out.println("BookmarkService initialized for testing");
    }

    @Test
    void getAllBookmarks() {
        // Arrange
        List<Bookmark> bookmarkList = Instancio.ofList(Bookmark.class)
                .size(5)
                .generate(field(Bookmark::createdAt), gen -> gen.temporal().localDateTime())
                .create();
        bookmarkList.forEach(bookmarkRepository::save);
        Integer pageNumber = 0, pageSize = 5; // Adjust page size as per your test data

        // Act
        ResponseEntity<?> responseEntity = bookmarkService.getAllBookmarks(pageNumber, pageSize);

        // Assertions
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        if (Objects.nonNull(responseEntity.getBody()) && responseEntity.getBody() instanceof Map<?, ?> body) {
            if (body.containsKey("data") && body.get("data") instanceof List<?> bookmarks) {
                assertThat(bookmarks.isEmpty()).isFalse();
                assertThat(bookmarks.size()).isEqualTo(5);
            }
        }
    }

    @Test
    void getBookmarkById() {
        // Arrange
        Bookmark bookmarkData = Instancio.of(Bookmark.class)
                .generate(field(Bookmark::createdAt), gen -> gen.temporal().localDateTime())
                .create();
        Long id = bookmarkRepository.save(bookmarkData);

        // Act
        ResponseEntity<?> responseEntity = bookmarkService.getBookmarkById(id);

        // Assertions
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        if (Objects.nonNull(responseEntity.getBody()) && responseEntity.getBody() instanceof Map<?, ?> body) {
            if (body.containsKey("data") && body.get("data") instanceof Bookmark bookmark) {
                assertThat(bookmark).isNotNull();
                assertThat(bookmark.id()).isEqualTo(id);
            }
        }
    }

    @Test
    void createBookmark() {
        // Arrange
        Bookmark bookmarkData = Instancio.of(Bookmark.class)
                .generate(field(Bookmark::createdAt), gen -> gen.temporal().localDateTime())
                .create();
        // Act
        ResponseEntity<?> responseEntity = bookmarkService.createBookmark(bookmarkData);

        // Assertions
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        if (Objects.nonNull(responseEntity.getBody()) && responseEntity.getBody() instanceof Map<?, ?> body) {
            if (body.containsKey("id") && body.get("id") instanceof Long id) {
                assertThat(id).isNotNull();
            }
            if (body.containsKey("message") && body.get("message") instanceof String message) {
                assertThat(message).isEqualTo("Bookmark created successfully");
            }
        }
    }

    @Test
    void updateBookmark() {
        // Arrange
        Bookmark bookmarkData = Instancio.of(Bookmark.class)
                .generate(field(Bookmark::createdAt), gen -> gen.temporal().localDateTime())
                .create();
        Long id = bookmarkRepository.save(bookmarkData);
        bookmarkData = new Bookmark(
                id,
                "Updated Title",
                "https://updatedurl.com",
                LocalDateTime.now());
        // Act
        ResponseEntity<?> responseEntity = bookmarkService.updateBookmark(bookmarkData);

        // Assertions
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        if (Objects.nonNull(responseEntity.getBody()) && responseEntity.getBody() instanceof Map<?, ?> body) {
            if (body.containsKey("message") && body.get("message") instanceof String message) {
                assertThat(message).isEqualTo("Bookmark updated successfully");
            }
        }
    }

    @Test
    void deleteBookmarkById() {
        // Arrange
        Bookmark bookmarkData = Instancio.of(Bookmark.class)
                .generate(field(Bookmark::createdAt), gen -> gen.temporal().localDateTime())
                .create();
        Long id = bookmarkRepository.save(bookmarkData);

        // Act
        ResponseEntity<?> responseEntity = bookmarkService.deleteBookmarkById(id);

        // Assertions
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        if (Objects.nonNull(responseEntity.getBody()) && responseEntity.getBody() instanceof Map<?, ?> body) {
            if (body.containsKey("message") && body.get("message") instanceof String message) {
                assertThat(message).isEqualTo("Bookmark deleted successfully");
            }
        }
    }
}

package io.akikr.demopostgredbapp.bookmark;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
class BookmarkRepository {

    private final JdbcClient jdbcClient;

    public BookmarkRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional(readOnly = true)
    public List<Bookmark> findAll(Integer pageNumber, Integer pageSize) throws IllegalStateException {
        final String SELECT_BOOKMARKS_QUERY = """
                SELECT id, title, url, created_at FROM bookmarks
                ORDER BY id
                OFFSET :offset LIMIT :limit
                """;

        // Checking for valid pageNumber or pageSize
        isPageNumberOrPageSizeValid(pageNumber, pageSize);
        // Getting offset value from pageNumber and pageSize
        var offset =  (pageNumber * pageSize);

        return jdbcClient.sql(SELECT_BOOKMARKS_QUERY)
                .param("offset", offset)
                .param("limit", pageSize)
                .query(Bookmark.class)
                .list();
    }

    private static void isPageNumberOrPageSizeValid(Integer pageNumber, Integer pageSize) throws IllegalStateException {
        if(Objects.isNull(pageNumber) || pageNumber < 0){
            throw new IllegalArgumentException("pageNumber must be greater than 0");
        }
        if(Objects.isNull(pageSize) || pageSize <= 0){
            throw new IllegalArgumentException("pageSize must be greater than 0");
        }
    }

    @Transactional(readOnly = true)
    public Optional<Bookmark> findById(Long id) throws IllegalArgumentException {
        final String SELECT_BOOKMARK_BY_ID_QUERY = """
                SELECT id, title, url, created_at FROM bookmarks WHERE id = :id
                """;

        return jdbcClient.sql(SELECT_BOOKMARK_BY_ID_QUERY)
                .param("id", id)
                .query(Bookmark.class)
                .optional();
    }

    @Transactional
    public Long save(Bookmark bookmark) {
        final String INSERT_BOOKMARK_QUERY = """
                INSERT INTO bookmarks (title, url, created_at) VALUES (:title, :url, :createdAt)
                RETURNING id
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(INSERT_BOOKMARK_QUERY)
                .param("title", bookmark.title())
                .param("url", bookmark.url())
                .param("createdAt", Timestamp.from(Instant.now()))
                .update(keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    @Transactional
    public Boolean update(Bookmark bookmark) throws IllegalArgumentException {
        final String UPDATE_BOOKMARK_QUERY = """
                UPDATE bookmarks SET title = :title, url = :url WHERE id = :id
                """;

        int updatedCount = jdbcClient.sql(UPDATE_BOOKMARK_QUERY)
                .param("id", bookmark.id())
                .param("title", bookmark.title())
                .param("url", bookmark.url())
                .update();
        if (updatedCount == 0) {
            throw new IllegalStateException("Bookmark NOT found with id: " + bookmark.id());
        }
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean deleteById(Long id) throws IllegalArgumentException {
        final String DELETE_BOOKMARK_BY_ID_QUERY = """
                DELETE FROM bookmarks WHERE id = :id
                """;

        int deleted = jdbcClient.sql(DELETE_BOOKMARK_BY_ID_QUERY)
                .param("id", id)
                .update();
        if (deleted == 0) {
            throw new IllegalStateException("Bookmark NOT found with id: " + id);
        }
        return Boolean.TRUE;
    }
}

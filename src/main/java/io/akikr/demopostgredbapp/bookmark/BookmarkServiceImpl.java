package io.akikr.demopostgredbapp.bookmark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@Service
final class BookmarkServiceImpl implements BookmarkService {

    private static final Logger log = LoggerFactory.getLogger(BookmarkServiceImpl.class);
    private final BookmarkRepository bookmarkRepository;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public ResponseEntity<?> getAllBookmarks(Integer pageNumber, Integer pageSize) {
        log.info("Fetching all bookmarks for pageNumber[{}] and pageSize[{}]", pageNumber, pageSize);
        try {
            List<Bookmark> bookmarks = bookmarkRepository.findAll(pageNumber, pageSize);
            if (bookmarks.isEmpty()) {
                log.warn("No bookmarks found for pageNumber[{}] and pageSize[{}]", pageNumber, pageSize);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Bookmarks not found"));
            }
            return ResponseEntity.ok()
                    .body(Map.of("data", bookmarks));
        } catch (Exception e) {
            log.error("Error fetching all bookmarks for pageNumber[{}] and pageSize[{}], due to: {}", pageNumber, pageSize, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while fetching the bookmark"));
        }
    }

    @Override
    public ResponseEntity<?> getBookmarkById(Long id) {
        log.info("Fetching bookmark by ID: {}", id);
        try {
            Bookmark bookmark = bookmarkRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("Bookmark NOT found with id: " + id));
            return ResponseEntity.ok()
                    .body(Map.of("data", bookmark));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error fetching bookmark by ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while fetching the bookmark"));
        }
    }

    @Override
    public ResponseEntity<?> createBookmark(Bookmark bookmark) {
        log.info("Creating bookmark with title: {}", bookmark.title());
        try {
            Long savedId = requireNonNull(bookmarkRepository.save(bookmark), "Failed to create bookmark");
            log.info("Bookmark created successfully with ID: {}", savedId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("id", savedId, "message", "Bookmark created successfully"));
        } catch (Exception e) {
            log.error("Error creating bookmark: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> updateBookmark(Bookmark bookmark) {
        log.info("Updating bookmark with ID: {}", bookmark.id());
        try {
            Boolean isUpdated = bookmarkRepository.update(bookmark);
            if (isUpdated) {
                log.info("Bookmark with ID: {} updated successfully", bookmark.id());
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(Map.of("message", "Bookmark updated successfully"));
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Bookmark not found with ID: " + bookmark.id()));
    }

    @Override
    public ResponseEntity<?> deleteBookmarkById(Long id) {
    log.info("Deleting bookmark with ID: {}", id);
        try {
            Boolean isDeleted = bookmarkRepository.deleteById(id);
            if (isDeleted) {
                log.info("Bookmark with ID: {} deleted successfully", id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(Map.of("message", "Bookmark deleted successfully"));
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Bookmark not found with ID: " + id));
    }
}

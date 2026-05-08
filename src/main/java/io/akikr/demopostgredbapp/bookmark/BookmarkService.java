package io.akikr.demopostgredbapp.bookmark;

import java.util.Map;
import org.springframework.http.ResponseEntity;

sealed interface BookmarkService permits BookmarkServiceImpl {

    ResponseEntity<Map<String, Object>> getAllBookmarks(Integer pageNumber, Integer pageSize);

    ResponseEntity<Map<String, Object>> getBookmarkById(Long id);

    ResponseEntity<Map<String, Object>> createBookmark(Bookmark bookmark);

    ResponseEntity<Map<String, Object>> updateBookmark(Bookmark bookmark);

    ResponseEntity<Map<String, Object>> deleteBookmarkById(Long id);
}

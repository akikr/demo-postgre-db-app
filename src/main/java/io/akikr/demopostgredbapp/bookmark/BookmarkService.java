package io.akikr.demopostgredbapp.bookmark;

import org.springframework.http.ResponseEntity;

sealed interface BookmarkService permits BookmarkServiceImpl{

    ResponseEntity<?> getAllBookmarks(Integer pageNumber, Integer pageSize);

    ResponseEntity<?> getBookmarkById(Long id);

    ResponseEntity<?> createBookmark(Bookmark bookmark);

    ResponseEntity<?> updateBookmark(Bookmark bookmark);

    ResponseEntity<?> deleteBookmarkById(Long id);
}

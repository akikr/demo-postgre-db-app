package io.akikr.demopostgredbapp.bookmark;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping(path = "/bookmarks")
    public ResponseEntity<Map<String, Object>> getAllBookmarks(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize) {
        return bookmarkService.getAllBookmarks(pageNumber, pageSize);
    }

    @GetMapping(path = "/bookmarks/{id}")
    public ResponseEntity<Map<String, Object>> getBookmarkById(@PathVariable(name = "id") Long id) {
        return bookmarkService.getBookmarkById(id);
    }

    @PostMapping(path = "/bookmarks")
    public ResponseEntity<Map<String, Object>> createBookmark(@RequestBody Bookmark bookmark) {
        return bookmarkService.createBookmark(bookmark);
    }

    @PutMapping(path = "/bookmarks")
    public ResponseEntity<Map<String, Object>> updateBookmark(@RequestBody Bookmark bookmark) {
        return bookmarkService.updateBookmark(bookmark);
    }

    @DeleteMapping(path = "/bookmarks/{id}")
    public ResponseEntity<Map<String, Object>> deleteBookmarkById(@PathVariable(name = "id") Long id) {
        return bookmarkService.deleteBookmarkById(id);
    }
}

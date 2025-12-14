package io.akikr.demopostgredbapp.bookmark;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(BookmarkController.class)
class BookmarkControllerWithMockMvcTesterTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private BookmarkServiceImpl bookmarkService;

    @Test
    @DisplayName("GET /v1/bookmarks should return list of data")
    void getAllBookmarks() {
        //Mock
        Map<String, Object> data = Map.of("data", List.of(
                new Bookmark(101L, "Demo-Title", "https://demo-url", LocalDateTime.now()),
                new Bookmark(102L, "Test-Title", "https://test-url", LocalDateTime.now())
        ));
        when(bookmarkService.getAllBookmarks(anyInt(), anyInt())).thenReturn(ResponseEntity.ok().body(data));

        //Arrange & Act
        var result = mockMvcTester.get().uri("/v1/bookmarks").exchange();

        //Assertion
        result.assertThat().hasStatusOk();
        result.assertThat().hasContentTypeCompatibleWith(MediaType.APPLICATION_JSON);
        result.assertThat().bodyJson()
                .extractingPath("$.data")
                .asArray()
                .isNotEmpty();

        //Arrange & Act
        var resultWithPagination = mockMvcTester.get().uri("/v1/bookmarks")
                .requestAttr("page", "1")
                .requestAttr("size", "1")
                .exchange();

        //Assertion
        resultWithPagination.assertThat().hasStatusOk();
        resultWithPagination.assertThat().hasContentTypeCompatibleWith(MediaType.APPLICATION_JSON);
        resultWithPagination.assertThat().bodyJson()
                .extractingPath("$.data")
                .asArray()
                .isNotEmpty();

        verify(bookmarkService, times(2)).getAllBookmarks(anyInt(), anyInt());
    }

    @Test
    @DisplayName("GET /v1/bookmarks/{id} should return bookmark data of {id}")
    void getBookmarkById() {
        //Mock
        Map<String, Object> data = Map.of("data", List.of(
                new Bookmark(101L, "Demo-Title", "https://demo-url", LocalDateTime.now())
        ));
        when(bookmarkService.getBookmarkById(anyLong())).thenReturn(ResponseEntity.ok().body(data));

        //Arrange & Act
        var result = mockMvcTester.get().uri("/v1/bookmarks/101").exchange();

        //Assertion
        result.assertThat().hasStatusOk();
        result.assertThat().hasContentTypeCompatibleWith(MediaType.APPLICATION_JSON);
        result.assertThat().bodyJson()
                .extractingPath("$.data.[0].id")
                .isEqualTo(101);
        result.assertThat().bodyJson()
                .extractingPath("$.data.[0].title")
                .isEqualTo("Demo-Title");

        //Verify
        verify(bookmarkService, times(1)).getBookmarkById(anyLong());
    }

    @Test
    @DisplayName("POST /v1/bookmarks should return HttpStatus CREATED")
    void createBookmark() {
        //Arrange
        var created = new Bookmark(201L, "New-Title", "https://new-url", LocalDateTime.now());
        //Mock
        when(bookmarkService.createBookmark(any(Bookmark.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(Map.of("data", created)));

        //Act
        var result = mockMvcTester.post()
                .uri("/v1/bookmarks")
                .content("""
                        {
                            "title": "New-Title",
                            "url": "https://new-url"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        //Assertion
        result.assertThat().hasStatus(HttpStatus.CREATED);
        result.assertThat().hasContentTypeCompatibleWith(MediaType.APPLICATION_JSON);
        result.assertThat().bodyJson()
                .extractingPath("$.data.id")
                .isEqualTo(201);
        result.assertThat().bodyJson()
                .extractingPath("$.data.title")
                .isEqualTo("New-Title");

        //Verify
        verify(bookmarkService, times(1)).createBookmark(any(Bookmark.class));
    }

    @Test
    @DisplayName("PUT /v1/bookmarks/{id} should return HttpStatus NO_CONTENT")
    void updateBookmark() {
        //Mock
        when(bookmarkService.updateBookmark(any(Bookmark.class))).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(Map.of("data", "Bookmark updated successfully")));

        //Arrange & Act
        var result = mockMvcTester.put()
                .uri("/v1/bookmarks")
                .content("""
                        {
                            "id": 101,
                            "title": "Updated-Title",
                            "url": "https://updated-url"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        //Assertion
        result.assertThat().hasStatus(HttpStatus.NO_CONTENT);
        result.assertThat().hasContentTypeCompatibleWith(MediaType.APPLICATION_JSON);

        //Verify
        verify(bookmarkService, times(1)).updateBookmark(any(Bookmark.class));
    }

    @Test
    @DisplayName("DELETE /v1/bookmarks/{id} should return HttpStatus NO_CONTENT")
    void deleteBookmarkById() {
        //Mock
        when(bookmarkService.deleteBookmarkById(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(Map.of("data", "Bookmark deleted successfully")));

        //Arrange & Act
        var result = mockMvcTester.delete().uri("/v1/bookmarks/101").exchange();

        //Assertion
        result.assertThat().hasStatus(HttpStatus.NO_CONTENT);
        result.assertThat().hasContentTypeCompatibleWith(MediaType.APPLICATION_JSON);

        //Verify
        verify(bookmarkService, times(1)).deleteBookmarkById(anyLong());
    }
}

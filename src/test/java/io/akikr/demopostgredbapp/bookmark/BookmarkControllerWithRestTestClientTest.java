package io.akikr.demopostgredbapp.bookmark;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

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
@AutoConfigureRestTestClient
class BookmarkControllerWithRestTestClientTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private BookmarkServiceImpl bookmarkService;

    @Test
    @DisplayName("GET /v1/bookmarks should return list of data")
    void getAllBookmarks() {
        //Arrange
        Map<String, Object> data = Map.of("data", List.of(
                new Bookmark(101L, "Demo-Title", "https://demo-url", LocalDateTime.now()),
                new Bookmark(102L, "Test-Title", "https://test-url", LocalDateTime.now())
        ));

        //Mock
        when(bookmarkService.getAllBookmarks(anyInt(), anyInt())).thenReturn(ResponseEntity.ok().body(data));

        //Act & Assert
        restTestClient.get().uri("/v1/bookmarks")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.data.[0].id")
                .isEqualTo(101);

        //Arrange
        Map<String, Object> dataWithPagination = Map.of("data", List.of(
                new Bookmark(102L, "Test-Title", "https://test-url", LocalDateTime.now())
        ));

        //Mock
        when(bookmarkService.getAllBookmarks(anyInt(), anyInt())).thenReturn(ResponseEntity.ok().body(dataWithPagination));

        //Act & Assert
        restTestClient.get().uri("/v1/bookmarks")
                .attribute("page", "1")
                .attribute("size", "1")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.data.[0].id")
                .isEqualTo(102);

        //Verify
        verify(bookmarkService, Mockito.times(2)).getAllBookmarks(anyInt(), anyInt());
    }

    @Test
    @DisplayName("GET /v1/bookmarks/{id} should return bookmark data of {id}")
    void getBookmarkById() {
        //Mock
        Map<String, Object> data = Map.of("data", List.of(
                new Bookmark(101L, "Demo-Title", "https://demo-url", LocalDateTime.now())
        ));
        when(bookmarkService.getBookmarkById(anyLong())).thenReturn(ResponseEntity.ok().body(data));

        //Act & Assert
        restTestClient.get().uri("/v1/bookmarks/101")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.data.[0].id")
                .isEqualTo(101)
                .jsonPath("$.data.[0].title")
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

        //Act & Assert
        restTestClient.post().uri("/v1/bookmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {
                            "title": "New-Title",
                            "url": "https://new-url"
                        }
                        """)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CREATED)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.data.id")
                .isEqualTo(201)
                .jsonPath("$.data.title")
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

        //Act & Assert
        restTestClient.put().uri("/v1/bookmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {
                            "id": 101,
                            "title": "Updated-Title",
                            "url": "https://updated-url"
                        }
                        """)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.NO_CONTENT)
                .expectHeader().contentType(MediaType.APPLICATION_JSON);

        //Verify
        verify(bookmarkService, times(1)).updateBookmark(any(Bookmark.class));
    }

    @Test
    @DisplayName("DELETE /v1/bookmarks/{id} should return HttpStatus NO_CONTENT")
    void deleteBookmarkById() {
        //Mock
        when(bookmarkService.deleteBookmarkById(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(Map.of("data", "Bookmark deleted successfully")));

        //Act & Assert
        restTestClient.delete().uri("/v1/bookmarks/101")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.NO_CONTENT)
                .expectHeader().contentType(MediaType.APPLICATION_JSON);

        //Verify
        verify(bookmarkService, times(1)).deleteBookmarkById(anyLong());
    }
}

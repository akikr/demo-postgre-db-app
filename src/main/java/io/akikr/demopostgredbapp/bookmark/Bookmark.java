package io.akikr.demopostgredbapp.bookmark;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/// This class represents a Bookmark entity with fields for id, title, url, and createdAt timestamp.
/// The Bookmark class is a record, which is a special kind of class in Java that is used to model immutable data.

record Bookmark(Long id,
                       @NotNull(message = "Title cannot be NULL") @NotBlank(message = "Title cannot be Blank") String title,
                       @NotNull(message = "URL cannot be NULL") @NotBlank(message = "URL cannot be Blank") String url,
                       LocalDateTime createdAt) {
}

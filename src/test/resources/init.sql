CREATE TABLE IF NOT EXISTS bookmarks
(
    id         bigserial primary key,
    title      varchar   not null,
    url        varchar   not null,
    created_at timestamp
);

-- Index to fetch bookmarks order-by created_at: If multiple rows share same created_at, add id as tiebreaker
CREATE INDEX idx_bookmarks_created_id ON bookmarks (created_at DESC, id DESC);

-- Duplicate bookmark urls are not allowed
CREATE UNIQUE INDEX uq_bookmarks_url ON bookmarks (url);

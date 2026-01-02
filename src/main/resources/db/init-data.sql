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


INSERT INTO bookmarks(title, url, created_at)
VALUES ('How (not) to ask for Technical Help?', 'https://sivalabs.in/how-to-not-to-ask-for-technical-help',
        CURRENT_TIMESTAMP),
       ('Getting Started with Kubernetes', 'https://sivalabs.in/getting-started-with-kubernetes', CURRENT_TIMESTAMP),
       ('Few Things I learned in the HardWay in 15 years of my career',
        'https://sivalabs.in/few-things-i-learned-the-hardway-in-15-years-of-my-career', CURRENT_TIMESTAMP),
       ('All the resources you ever need as a Java & Spring application developer',
        'https://sivalabs.in/all-the-resources-you-ever-need-as-a-java-spring-application-developer',
        CURRENT_TIMESTAMP),
       ('SpringBoot Integration Testing using Testcontainers Starter',
        'https://sivalabs.in/spring-boot-integration-testing-using-testcontainers-starter', CURRENT_TIMESTAMP),
       ('Testing SpringBoot Applications', 'https://sivalabs.in/spring-boot-testing', CURRENT_TIMESTAMP);

CREATE TABLE IF NOT EXISTS pages (
    id smallserial primary key not null,
    page varchar unique,
    verbs varchar,
    content_type varchar,
    path varchar
);
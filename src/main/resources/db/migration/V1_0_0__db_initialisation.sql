CREATE TABLE IF NOT EXISTS pages (
    id smallserial primary key not null,
    page_name varchar unique,
    verbs smallint[],
    content_type varchar,
    path varchar
);
CREATE TABLE categories (
    category_id   VARCHAR(60),
    category_name VARCHAR2(100) NOT NULL UNIQUE,
    CONSTRAINT categories_pk
        PRIMARY KEY (category_id)
);

ALTER TABLE tasks
    ADD category_id VARCHAR(60) NULL;

ALTER TABLE tasks
    ADD CONSTRAINT tasks_categories_category_id_fk
        FOREIGN KEY (category_id) REFERENCES categories (category_id);
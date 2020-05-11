INSERT INTO categories (category_id, category_name)
VALUES ('1', 'Operators');
INSERT INTO categories (category_id, category_name)
VALUES ('2', 'Arrays');
INSERT INTO categories (category_id, category_name)
VALUES ('3', 'Strings');
INSERT INTO categories (category_id, category_name)
VALUES ('4', 'Collections');
INSERT INTO categories (category_id, category_name)
VALUES ('5', 'Exceptions');
INSERT INTO categories (category_id, category_name)
VALUES ('6', 'Lambda & Streams');
INSERT INTO categories (category_id, category_name)
VALUES ('7', 'DateTime');
INSERT INTO categories (category_id, category_name)
VALUES ('8', 'Concurrency');

UPDATE tasks
SET category_id = '6'
WHERE category_id IS NULL;

ALTER TABLE tasks
    MODIFY category_id VARCHAR(60) NOT NULL;
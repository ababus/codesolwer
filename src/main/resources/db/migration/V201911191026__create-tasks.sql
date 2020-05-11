CREATE TABLE tasks (
    task_id   VARCHAR(60) NOT NULL,
    task_title VARCHAR(100) NOT NULL,
    task_body VARCHAR2 (1000) NOT NULL,
    description VARCHAR2 (1000) DEFAULT 'this task has not been formulated yet' NOT NULL,
    PRIMARY KEY (task_id)
);
CREATE TABLE tests (
    test_id VARCHAR2(60) NOT NULL,
    task_id VARCHAR2(60) NOT NULL,
    test_file VARCHAR2(4000) NOT NULL,
    PRIMARY KEY (test_id),
    CONSTRAINT test_task_id
        FOREIGN KEY (task_id)
            REFERENCES tasks (task_id)
);

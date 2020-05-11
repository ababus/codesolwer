CREATE TABLE users_tasks (
    user_id VARCHAR2 (60) NOT NULL,
    task_id VARCHAR2 (60) NOT NULL,
    attempts INTEGER DEFAULT 0 NOT NULL,
    passed NUMBER (1) DEFAULT 0 NOT NULL,
    PRIMARY KEY (user_id, task_id),
    CONSTRAINT fk_task_id
        FOREIGN KEY (task_id)
            REFERENCES tasks (task_id),
    CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
);
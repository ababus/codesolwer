ALTER TABLE users
    ADD number_of_attempts NUMBER DEFAULT 0 NOT NULL;

ALTER TABLE users
    ADD active NUMBER(1) DEFAULT 1 NOT NULL;
CREATE TABLE users (
    user_id  VARCHAR(60) NOT NULL PRIMARY KEY,
    user_name VARCHAR(45) NOT NULL UNIQUE,
    password  VARCHAR(100) NOT NULL,
    role      VARCHAR(5) DEFAULT 'USER',
    CHECK (role IN ('USER', 'ADMIN'))
);
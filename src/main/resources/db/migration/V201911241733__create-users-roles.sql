CREATE TABLE users_roles (
    user_id VARCHAR(60) NOT NULL,
    role_id VARCHAR(60) NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_role_id
        FOREIGN KEY (role_id)
            REFERENCES roles (role_id),
    CONSTRAINT fk_user_role_id
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
);


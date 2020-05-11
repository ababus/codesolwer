INSERT INTO users
(user_id,
user_name,
password)
VALUES
('2',
'admin',
-- actual password: @dC8,LM^p
'$2a$10$lmwf9WHen/fblOid0fud1.5GdWdVzvXlCBQMNjVtxQtdG7la7w6V2');

INSERT INTO users_roles
(user_id,
role_id)
VALUES
('2',
'2');
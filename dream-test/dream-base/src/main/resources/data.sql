DELETE
FROM user;

INSERT INTO user (id, name, age, email, tenant_id, dept_id, del_flag)
VALUES (1, 'Jone', 18, 'test1', 1, 1, 0),
       (2, 'Jack', 20, 'test2', 1, 1, 0),
       (3, 'Tom', 28, 'test3', 1, 1, 0),
       (4, 'Sandy', 21, 'test4', 2, 2, 0),
       (5, 'Billie', 24, 'test5', 2, 2, 0);
DELETE
FROM blog;
INSERT INTO blog (user_id, name, publish_time, tenant_id)
VALUES (1, 'java', '2022-07-01', 1),
       (1, 'java核心技术', '2022-07-02', 1),
       (2, 'c++', '2022-07-01', 1),
       (2, 'c++核心技术', '2022-07-02', 1),
       (3, 'python', '2022-07-01', 1),
       (3, 'python核心技术', '2022-07-02', 1),
       (4, 'go', '2022-07-01', 2),
       (4, 'go核心技术', '2022-07-02', 2),
       (5, 'web', '2022-07-01', 2),
       (5, 'web核心技术', '2022-07-02', 2);

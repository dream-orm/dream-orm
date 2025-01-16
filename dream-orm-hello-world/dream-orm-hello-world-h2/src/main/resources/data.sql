DELETE
FROM account;

DELETE
FROM dept;

INSERT INTO account (id, name, age, email, tenant_id, dept_id, del_flag)
VALUES (1, 'Jone', 18, 'test1', 1, 1, 0),
       (2, 'Jack', 20, 'test2', 1, 1, 0),
       (3, 'Tom', 28, 'test3', 1, 1, 0),
       (4, 'Sandy', 21, 'test4', 2, 2, 0),
       (5, 'Billie', 24, 'test5', 2, 2, 0);

INSERT INTO dept(id, name, tenant_id, del_flag)
VALUES (1, 'dept1', 1, 0),
       (2, 'dept2', 2, 0)

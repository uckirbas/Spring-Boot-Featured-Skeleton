-- New Migration
INSERT INTO privileges (id, created, deleted, last_updated, uuid_str, label, name, created_by_id, updated_by_id)
VALUES (8, '2020-02-02 16:36:39', false, '2020-02-02 16:36:39', '2283968a-94c3-48d2-8148-64964fa8c33d',
        'Administration', 'ADMINISTRATION', null, null);
INSERT INTO privileges (id, created, deleted, last_updated, uuid_str, label, name, created_by_id, updated_by_id)
VALUES (9, '2020-02-02 16:36:39', false, '2020-02-02 16:36:39', 'ddeaf964-67a0-4187-8fc7-2ed745416e55',
        'Access User Resources', 'ACCESS_USER_RESOURCES', null, null);

INSERT INTO roles_privileges
VALUES ((SELECT id FROM roles WHERE name = 'ROLE_ADMIN'), (SELECT id FROM privileges WHERE name = 'ADMINISTRATION'));
INSERT INTO roles_privileges
VALUES ((SELECT id FROM roles WHERE name = 'ROLE_USER'),
        (SELECT id FROM privileges WHERE name = 'ACCESS_USER_RESOURCES'));
DELETE FROM vote;
DELETE FROM dish;
DELETE FROM menu;
DELETE FROM restaurant;
DELETE FROM user_roles;
DELETE FROM users;

INSERT INTO users (id, email, password) VALUES
  (100000, 'user@yandex.ru', '$2a$08$QtgY0EZyruarCcfdU8E/hePeEHjfuyL9iFfwFW1mC.A2tDv9GMq3e'),
  (100001, 'admin@gmail.com', '$2a$08$4hMIdF/hu1F45srb7QKSDut5sGyDF7cLIwBOjerEL5xNOCYlHUkCS');

INSERT INTO user_roles (role, user_id) VALUES
  ('USER', 100000),
  ('ADMIN', 100001),
  ('USER', 100001);

INSERT INTO restaurant (id, name) VALUES
  (100002, 'First place'),
  (100003, 'Second place'),
  (100004, 'Third place');

INSERT INTO menu (id, date, restaurant_id) VALUES
  (100005, CURRENT_DATE, 100002),
  (100006, CURRENT_DATE, 100003),
  (100007, DATEADD('DAY', '-1', CURRENT_DATE), 100004);

INSERT INTO dish (id, name, price, menu_id) VALUES
    (100008, 'First main', 100, 100005),
    (100009, 'First best', 50, 100005),
    (100010, 'First cheap', 10, 100005),
    (100011, 'Second main', 1000, 100006),
    (100012, 'Second cheap', 200, 100006);

INSERT INTO vote (id, user_id, menu_id, vote_date) VALUES
    (100014, 100001, 100005, CURRENT_DATE),
    (100015, 100000, 100007, DATEADD('DAY', '-1', CURRENT_DATE));
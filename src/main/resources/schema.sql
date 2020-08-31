DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100050 INCREMENT BY 1;

CREATE TABLE restaurant
(
    id   INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE menu
(
    id            INTEGER PRIMARY KEY,
    date          TIMESTAMP NOT NULL,
    restaurant_id INTEGER   NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX menu_date_idx ON menu(id, date);

CREATE TABLE dish
(
    id      INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    name    VARCHAR NOT NULL,
    price   INTEGER NOT NULL,
    menu_id INTEGER NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id       INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    email    VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE vote
(
    id        INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    user_id   INTEGER NOT NULL,
    menu_id   INTEGER NOT NULL,
    vote_date DATE    NOT NULL,
    CONSTRAINT user_unique_date_idx UNIQUE (user_id, vote_date),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
)
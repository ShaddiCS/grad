DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

CREATE TABLE restaurant
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE menu
(
    id            INTEGER PRIMARY KEY,
    date          TIMESTAMP NOT NULL,
    restaurant_id INTEGER   NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE dish
(
    id      INTEGER PRIMARY KEY,
    name    VARCHAR NOT NULL,
    price   float8  NOT NULL,
    menu_id INTEGER NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id       INTEGER PRIMARY KEY,
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
    id        INTEGER PRIMARY KEY,
    user_id   INTEGER NOT NULL,
    menu_id   INTEGER NOT NULL,
    vote_date DATE    NOT NULL,
    vote_time TIME    NOT NULL,
    CONSTRAINT user_menu_idx UNIQUE (user_id, menu_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
)
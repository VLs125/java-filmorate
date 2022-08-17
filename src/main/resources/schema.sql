CREATE TABLE IF NOT EXISTS user
(
    id       INT AUTO_INCREMENT NOT NULL,
    email    VARCHAR(255)       NOT NULL,
    login    VARCHAR(255)       NOT NULL,
    name     VARCHAR(255)       NOT NULL,
    birthday DATE,
    CONSTRAINT pk_user PRIMARY KEY (
                                    id
        )
);

CREATE TABLE IF NOT EXISTS film
(
    id           INT AUTO_INCREMENT NOT NULL,
    name         VARCHAR(255)       NOT NULL,
    description  VARCHAR(255)       NOT NULL,
    release_date DATE               NOT NULL,
    duration     INT                NOT NULL,
    mpa_id       INT                NOT NULL,
    CONSTRAINT pk_film PRIMARY KEY (
                                    id
        )
);

CREATE TABLE IF NOT EXISTS mpa
(
    id    INT AUTO_INCREMENT NOT NULL,
    title VARCHAR(255)       NOT NULL,
    CONSTRAINT pk_mpa PRIMARY KEY (
                                   id
        )
);
CREATE TABLE IF NOT EXISTS film_rate
(
    id      INT AUTO_INCREMENT NOT NULL,
    film_id INT                NOT NULL,
    rate    INT,
    CONSTRAINT pk_film_rate PRIMARY KEY (id
        )
);

CREATE TABLE IF NOT EXISTS film_genres
(
    id       INT AUTO_INCREMENT NOT NULL,
    film_id  INT                NOT NULL,
    genre_id INT                NOT NULL,
    CONSTRAINT pk_film_genres PRIMARY KEY (id
        )
);

CREATE TABLE IF NOT EXISTS friendship
(
    id                   INT AUTO_INCREMENT NOT NULL,
    user_sender_invite   INT                NOT NULL,
    user_received_invite INT                NOT NULL,
    is_accepted          BOOLEAN            NOT NULL,
    CONSTRAINT pk_friendship PRIMARY KEY (
                                          id
        )
);

CREATE TABLE IF NOT EXISTS genre
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)       NOT NULL,
    CONSTRAINT pk_genre PRIMARY KEY (
                                     id
        )
);

CREATE TABLE IF NOT EXISTS liked_film
(
    user_id INT NOT NULL,
    film_id INT NOT NULL,
    CONSTRAINT pk_liked_film PRIMARY KEY (
                                          user_id, film_id
        )
);
ALTER TABLE IF EXISTS film_genres
    ADD CONSTRAINT IF NOT EXISTS fk_film_id FOREIGN KEY (film_id)
        REFERENCES film (id)
;
ALTER TABLE IF EXISTS film_genres
    ADD CONSTRAINT IF NOT EXISTS fk_genre_id FOREIGN KEY (genre_id)
        REFERENCES genre (id)
;

ALTER TABLE IF EXISTS film
    ADD CONSTRAINT IF NOT EXISTS fk_film_rating FOREIGN KEY (mpa_id)
        REFERENCES mpa (id);

ALTER TABLE IF EXISTS friendship
    ADD CONSTRAINT IF NOT EXISTS fk_friendship_user_sender_invite FOREIGN KEY (user_sender_invite)
        REFERENCES user (id);

ALTER TABLE IF EXISTS friendship
    ADD CONSTRAINT IF NOT EXISTS fk_friendship_user_received_invite FOREIGN KEY (user_received_invite)
        REFERENCES user (id);

ALTER TABLE IF EXISTS liked_film
    ADD CONSTRAINT IF NOT EXISTS fk_liked_film_user FOREIGN KEY (user_id)
        REFERENCES user (id);

ALTER TABLE IF EXISTS liked_film
    ADD CONSTRAINT IF NOT EXISTS fk_liked_film_film FOREIGN KEY (film_id)
        REFERENCES film (id);

ALTER TABLE IF EXISTS film_rate
    ADD CONSTRAINT IF NOT EXISTS fk_liked_film_user FOREIGN KEY (film_id)
        REFERENCES film (id);

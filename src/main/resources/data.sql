insert into MPA (id, title)
    values (1, 'G');
insert into MPA (id, title)
    values (2, 'PG ');
insert into MPA (id, title)
    values (3, 'PG-13');
insert into MPA (id, title)
    values (4, 'R ');
insert into MPA (id, title)
    values (5, 'NC-17');

insert into GENRE (id, NAME)
    values (1, 'Комедия');
insert into GENRE (id, NAME)
    values (2, 'Драма');
insert into GENRE (id, NAME)
    values (3, 'Мультфильм');
insert into GENRE (id, NAME)
    values (4, 'Триллер');
insert into GENRE (id, NAME)
    values (5, 'Документальный');
insert into GENRE (id, NAME)
    values (6, 'Боевик');

-- insert into FILM (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)
--     values ('Зачарованные', 'Великий сериал о зачарованных', '2002-10-23', 90, 1);
-- insert into FILM (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)
--     values ('Оригато', 'Великий сериал о оригато', '2002-10-23', 120, 1);
-- insert into FILM (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)
--     values ('Апельсин', 'Великий сериал о апельсинах', '2002-10-23', 65, 1);
--
-- insert into FILM_GENRES (ID, FILM_ID, GENRE_ID)
--     values (1, 2, 2);
-- insert into FILM_GENRES (ID, FILM_ID, GENRE_ID)
--     values (2, 3, 1);
-- insert into FILM_GENRES (ID, FILM_ID, GENRE_ID)
--     values (3, 2, 1);
-- insert into FILM_GENRES (FILM_ID, GENRE_ID)
--     values (2, 5);
--
-- insert into USER (EMAIL, LOGIN, NAME, BIRTHDAY)
--     values ('email@email.com', 'Nore', 'Дмитрий', '1989-04-17');
-- insert into USER (EMAIL, LOGIN, NAME, BIRTHDAY)
--     values ('nosemonster@gmail.com', 'slik12s', 'Алексей', '2002-10-23');
-- insert into USER (EMAIL, LOGIN, NAME, BIRTHDAY)
--     values ('superAcc@email.com', 'demon322', 'Абдул', '1999-01-01');
-- insert into USER (EMAIL, LOGIN, NAME, BIRTHDAY)
--     values ('cheburashka@email.com', 'ubiicaMamontov', 'Сашка', '1999-01-01');
--
-- insert into FRIENDSHIP (id, user_sender_invite, user_received_invite, is_accepted)
--     values (1, 1, 2, false);
-- insert into FRIENDSHIP (id, user_sender_invite, user_received_invite, is_accepted)
--     values (2, 1, 3, false);
-- insert into FRIENDSHIP (id, user_sender_invite, user_received_invite, is_accepted)
--     values (3, 1, 4, false);
-- insert into FRIENDSHIP (id, user_sender_invite, user_received_invite, is_accepted)
--     values (4, 4, 2, false);
-- insert into FRIENDSHIP (id, user_sender_invite, user_received_invite, is_accepted)
--     values (5, 3, 4, false);

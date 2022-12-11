INSERT INTO GENRE (NAME)
VALUES ('Fairy tale'), ('Poem');

INSERT INTO AUTHOR (NAME, SURNAME)
VALUES ('Kadzuo', 'Iwamura'),
       ('Alexander', 'Pushkin'),
       ('Julia', 'Donaldson');

INSERT INTO BOOK (TITLE, AUTHOR_ID, GENRE_ID)
VALUES ('14 wood mice. Breakfast', 1, 1),
       ('14 wood mice. Picnic', 1, 1),
       ('14 wood mice and the Winter Sledding Day', 1, 1),
       ('Ruslan and Ludmila', 2, 2),
       ('Snail and Kit', 3, 1);

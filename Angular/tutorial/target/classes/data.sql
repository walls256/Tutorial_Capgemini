
INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO client(name) VALUES ('Cliente 1');
INSERT INTO client(name) VALUES ('Cliente 2');
INSERT INTO client(name) VALUES ('Cliente 3');
INSERT INTO client(name) VALUES ('Cliente 4');
INSERT INTO client(name) VALUES ('Cliente 5');
INSERT INTO client(name) VALUES ('Cliente 6');

INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('2024-02-02', '2024-02-10', 1, 2);
INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('2024-02-13', '2024-02-16', 3, 5);
INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('2024-01-25', '2024-02-05', 5, 4);
INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('2024-01-24', '2024-02-06', 2, 6);
INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('2024-02-12', '2024-02-22', 2, 1);
INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('2024-02-24', '2024-02-26', 4, 4);
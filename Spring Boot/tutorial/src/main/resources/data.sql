
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

INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('02-02-2024', '10-02-2024', 1, 2);
INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('13-02-2024', '16-02-2024', 3, 5);
INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('25-01-2024', '05-02-2024', 5, 4);
INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('24-01-2024', '06-02-2024', 2, 6);
INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('12-02-2024', '22-02-2024', 2, 1);
INSERT INTO loan(start_date, end_date, game_id, client_id) VALUES ('24-02-2024', '26-02-2024', 4, 4);
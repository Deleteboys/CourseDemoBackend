-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- #################################
-- ### BENUTZER UND BERECHTIGUNGEN ###
-- #################################

-- Wichtiger Hinweis: Die Passwörter sind mit BCrypt gehasht.
-- Das Original-Passwort für beide Benutzer lautet: 'password123'
INSERT INTO users (id, username, email, password_hash, created_at, activated) VALUES
                                                                                  (1, 'admin', 'admin@example.com', '$2a$12$.d.WHEQXSk.SyglnMJSVK.SSP3aB9rLxCWDVjxvuQUFEdoWeZmWl.', NOW(), true),
                                                                                  (2, 'user', 'user@example.com', '$2a$12$.d.WHEQXSk.SyglnMJSVK.SSP3aB9rLxCWDVjxvuQUFEdoWeZmWl.', NOW(), true);

-- Berechtigungen erstellen
INSERT INTO permission (id, name, description) VALUES
                                                   (10, 'admin:full', 'Voller Zugriff auf alle administrativen Funktionen.'),
                                                   (11, 'user:read', 'Lesender Zugriff auf Kurse und Lektionen.');

-- Berechtigungen den Benutzern zuweisen
INSERT INTO user_permission (user_id, permission_id) VALUES
                                                         (1, 10), -- admin hat admin-Rechte
                                                         (1, 11), -- admin hat auch user-Rechte
                                                         (2, 11); -- user hat user-Rechte


-- #################################
-- ### KURSE UND LEKTIONEN       ###
-- #################################

INSERT INTO course (id, title, description, created_at) VALUES
                                                            (101, 'Java Grundlagen für Anfänger', 'Ein Kurs über die Grundlagen von Java, von Variablen bis zu einfachen Klassen.', NOW()),
                                                            (102, 'Quarkus Deep Dive', 'Lerne fortgeschrittene Konzepte in Quarkus, inklusive Security und Messaging.', NOW());

INSERT INTO lesson (id, course_id, title, content, order_index, created_at) VALUES
-- Lektionen für Java Grundlagen
(201, 101, 'Was ist Java?', 'Java ist eine objektorientierte Programmiersprache...', 1, NOW()),
(202, 101, 'Variablen und Datentypen', 'In Java gibt es primitive Datentypen und Referenztypen...', 2, NOW()),
(203, 101, 'Deine erste Methode', 'Eine Methode ist ein Block von Code, der eine Aufgabe erfüllt...', 3, NOW()),

-- Lektionen für Quarkus Deep Dive
(204, 102, 'Security mit Elytron', 'Sichere deine Endpunkte mit rollenbasiertem Zugriff.', 1, NOW()),
(205, 102, 'Asynchrones Messaging mit Kafka', 'Verarbeite Nachrichtenströme reaktiv und effizient.', 2, NOW());


-- #################################
-- ### BEWERTUNGEN               ###
-- #################################

INSERT INTO rating (id, lesson_id, user_id, score, comment, created_at) VALUES
                                                                            (301, 201, 2, 5, 'Super Einleitung, hat mir sehr geholfen!', NOW()),
                                                                            (302, 202, 2, 4, 'Das Thema war etwas trocken, aber gut erklärt.', NOW()),
                                                                            (303, 204, 1, 5, 'Das Security-Kapitel ist absolut essenziell. Top!', NOW());


-- WICHTIG: Die ID-Sequenzen müssen nach dem manuellen Einfügen aktualisiert werden,
-- damit die automatische ID-Vergabe in der Anwendung wieder funktioniert.
-- Setze den Startwert auf eine Zahl, die höher ist als die höchste manuell eingefügte ID.
ALTER SEQUENCE users_SEQ RESTART WITH 10;
ALTER SEQUENCE permission_SEQ RESTART WITH 20;
ALTER SEQUENCE course_SEQ RESTART WITH 110;
ALTER SEQUENCE lesson_SEQ RESTART WITH 210;
ALTER SEQUENCE rating_SEQ RESTART WITH 310;
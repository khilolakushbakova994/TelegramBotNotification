-- liquibase formatted sql

-- changeset myUser:1
CREATE TABLE notification_task
(
id REAL PRIMARY KEY,
chatId TEXT,
messageText TEXT);

-- changeset myUser:2

CREATE TABLE Reminder
(
id REAL PRIMARY KEY,
dateTime DATE,
reminderText TEXT);

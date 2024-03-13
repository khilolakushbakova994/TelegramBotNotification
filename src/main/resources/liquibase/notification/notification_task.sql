-- liquibase formatted sql

-- changeset myUser:1
CREATE TABLE notification_task
(
id BIGINT PRIMARY KEY,
chat_id TEXT,
message_Text TEXT,
notification_date_time TIMESTAMP);

-- changeset myUser:2

CREATE TABLE Reminder
(
id REAL PRIMARY KEY,
dateTime DATE,
reminderText TEXT);

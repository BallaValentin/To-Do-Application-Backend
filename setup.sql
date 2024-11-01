CREATE DATABASE IF NOT EXISTS ToDoDatabase;
USE ToDoDatabase;

CREATE TABLE IF NOT EXISTS ToDo(
	ID BIGINT PRIMARY KEY auto_increment,
    Title LONGTEXT,
    Description LONGTEXT,
    DueDate DATE,
    ImportanceLevel INT
);

SELECT * FROM ToDo;
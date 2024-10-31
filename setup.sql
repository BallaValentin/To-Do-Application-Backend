CREATE DATABASE IF NOT EXISTS ToDoDatabase;
USE ToDoDatabase;
CREATE TABLE IF NOT EXISTS ToDo(
	ID INT PRIMARY KEY auto_increment,
    Title LONGTEXT,
    Description LONGTEXT,
    DueDate DATE,
    ImportanceLevel INT
);
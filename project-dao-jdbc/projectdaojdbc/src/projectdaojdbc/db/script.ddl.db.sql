CREATE DATABASE projectjdbc;

CREATE TABLE DEPARTMENT (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE TESTE (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE SELLER (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(60) NOT NULL,
  EMAIL VARCHAR(100) NOT NULL,
  BIRTHDATE DATETIME NOT NULL,
  BASESALARY DOUBLE NOT NULL,
  DEPARTMENTID INT(11) NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (DEPARTMENTID) REFERENCES DEPARTMENT (ID)
);
drop database test;
create database test;
use test;

CREATE TABLE Usr (
	username VARCHAR(20) NOT NULL,
	firstname VARCHAR(20) NOT NULL,
	lastname VARCHAR(20) NOT NULL,
	password VARCHAR(20) NOT NULL,
	email VARCHAR(40) NOT NULL,
	PRIMARY KEY (username)
);
	
CREATE TABLE Album (
	albumid INTEGER NOT NULL AUTO_INCREMENT,
	title VARCHAR(50) NOT NULL,
	created DATE NOT NULL,
	lastupdated DATE NOT NULL,
	username VARCHAR(20) NOT NULL,
	PRIMARY KEY (albumid),
	FOREIGN KEY (username) REFERENCES Usr(username)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Photo (
	picid VARCHAR(40) NOT NULL,
	url VARCHAR(255) NOT NULL,
	format CHAR(4) NOT NULL,
	datetaken DATE NOT NULL,
	PRIMARY KEY (picid, format)
);

CREATE TABLE Contain (
	albumid INTEGER NOT NULL,
	picid VARCHAR(40) NOT NULL,
	caption VARCHAR(255) NOT NULL,
	sequencenum INTEGER NOT NULL,
	PRIMARY KEY (albumid, caption,picid, sequencenum)
);

CREATE TABLE OutputAtoC (
	containpicid VARCHAR(40) NOT NULL,
	containcaption VARCHAR(255) NOT NULL,
	albumtitle VARCHAR(50) NOT NULL,
	albumusername VARCHAR(20) NOT NULL,
	rownum INTEGER NOT NULL,
	PRIMARY KEY (rownum)
);


CREATE TABLE desiredoutput (
	usrusername VARCHAR(40) NOT NULL,
	usrfirstname VARCHAR(20) NOT NULL,
	albumtitle VARCHAR(50) NOT NULL,
	albumalbumid INTEGER NOT NULL,
	rownum INTEGER NOT NULL,
	PRIMARY KEY (rownum)
);
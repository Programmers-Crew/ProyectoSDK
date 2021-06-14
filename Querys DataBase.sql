create database BlinkID;
use BlinkID;

create table TypeDocuments(
	typeId int(5) primary key auto_increment,
    typeDesc varchar(50) not null unique
);

create table TypeSex(
	sexId int(5) primary key auto_increment,
    sexDesc varchar(50) not null unique
);

create table CivilStatus(
	statusId int(5) primary key auto_increment,
    statusDesc varchar(50) not null unique
);

create table DocumentData(
	documentId int(30) primary key,
    firstName varchar(50) not null,
    secondName varchar(50) not null,
    firstLasName varchar(50) not null,
    secondLasName varchar(50) not null,
    birthDate date not null,
    age int(5) not null,
    birthPlace varchar(50) not null,
    expeditionDate date not null,
    expirationDate date not null,
    address varchar(50) not null,
    nationality varchar(50) not null,
    shipper varchar(50) not null,
    sex int(5) not null,
	civilStatus int(5) not null,
    textMRX varchar(50) not null,
    optionalFieldOne varchar(50) not null,
    optionalFieldSecond varchar(50) not null,
    imgFront varchar(50) not null,
    imgLater varchar(50) not null,
    documentTyoe int(5) not null,
	CONSTRAINT FK_DocumentType FOREIGN KEY (documentTyoe) REFERENCES TypeDocuments(typeId),
    CONSTRAINT FK_DocumentSex FOREIGN KEY (sex) REFERENCES TypeSex(sexId),
    CONSTRAINT FK_DocumentCivilSatus FOREIGN KEY (civilStatus) REFERENCES CivilStatus(statusId)
);


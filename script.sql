create database dms;
use dms;

### date - kog datuma zadnji put se korisnik ulogovao
### time - vrijeme kada se korisnik zadnji put ulogovao
### keyValue - za QR kod koristim
create table if not exists users(
	username varchar(45) not null check(length(username)>=2),
    password char(64) not null check(length(password)=64),
    role_name varchar(45) not null check(length(role_name)>=2),
    date Date not null default("1970-05-05"),
    time Time not null default("12:00:00"),
    keyValue varchar(100) not null, #ovo jos treba provjeriti da li treba biti not null
	primary key(username)
);

create table if not exists UserActions(
	username varchar(45) not null,
    dateAction DATE not null,
    timeAction TIME not null,
    typeAction varchar(200) not null check(length(typeAction)>=2),
    documentName varchar(200) not null check(length(documentName)>=2),
    foreign key(username) references Users(username)
    on update cascade
    on delete restrict,
    primary key(username, timeAction, typeAction, documentName)
);

create table if not exists AdminD(
	username varchar(45) not null,
    path varchar(200) not null check(length(path)>=1),
    foreign key(username) references Users(username)
    on update cascade
    on delete restrict,
    primary key(username)
);

create table if not exists AdminS(
	username varchar(45) not null,
    foreign key(username) references Users(username)
    on update cascade
    on delete restrict,
    primary key(username)
);

create table if not exists Client(
	username varchar(45) not null,
    path varchar(200) not null check(length(path)>=1),
    domen varchar(45) not null check(length(domen)>=15), # 0.0.0.0.0.0.0.1 = 15 length chars
    flagCreate tinyint not null,
    flagRetrieve tinyint not null,
    flagUpdate tinyint not null,
    flagDelete tinyint not null,
    foreign key(username) references Users(username)
    on update cascade
    on delete restrict,
    primary key(username)
);

create user if not exists 'admin'@'localhost' identified by 'Admin#219A912';

GRANT SELECT, INSERT, UPDATE, DELETE ON dms.Users TO 'admin'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON dms.AdminD TO 'admin'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON dms.AdminS TO 'admin'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON dms.Client TO 'admin'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON dms.UserActions TO 'admin'@'localhost';

create user if not exists 'client'@'localhost' identified by 'Counter#719C917';

GRANT SELECT ON dms.Users TO 'client'@'localhost';
GRANT SELECT ON dms.AdminD TO 'client'@'localhost';
GRANT SELECT ON dms.Client TO 'client'@'localhost';

insert into users(username, password, role_name, keyValue) values("aa", "ed02457b5c41d964dbd2f2a609d63fe1bb7528dbe55e1abf5b52c249cd735797", "adminS", "D2IE652IHHS6S6KATPG3OOCPOSI2IW7A");
insert into adminS(username) values("aa");

insert into users(username, password, role_name, keyValue) values("bb", "4625fd63b0e96fc0d656ae7381605e48d4a0f63a319fc743adf22688613883c7", "adminD", "VAQAUM7DQKBUKEIIQVGWXQFEIZKS7LNT");
insert into adminD(username, path) values("bb", "cc");
insert into users(username, password, role_name, keyValue) values("cc", "8c0e24f73bf5fe793dc45e5f90b5a9682c0cb345f524ddef77ab2859b9352d52", "client", "K6QJQ4YUXL6IQDTIMEYKWQ6RSBMJTPQN");
insert into users(username, password, role_name, keyValue) values("dd", "c02d8e6211ef7dc5af42085cbb323a122b76905ac098467ea1f85465ec14429d", "client", "IN5VVAFRKO3COGUBZ25JT4QOJIIQAC3B");
insert into client(username, path, domen, flagCreate, flagRetrieve, flagDelete, flagUpdate) values("cc", "cc", "0:0:0:0:0:0:0:1", 1, 1, 1, 1);
insert into client(username, path, domen, flagCreate, flagRetrieve, flagDelete, flagUpdate) values("dd", "dd", "0:0:0:0:0:0:0:1", 0, 1, 0, 1);
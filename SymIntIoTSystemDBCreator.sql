create database symintiotsystem;
use symintiotsystem;
SET @@session.time_zone='+00:00';
SET SQL_SAFE_UPDATES=0;

create table devices
(Id int not null auto_increment primary key,
Mac varchar(45) not null,
Description varchar(42),
Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
Updated TIMESTAMP DEFAULT 0 ON UPDATE CURRENT_TIMESTAMP);

create table times
(Id int not null auto_increment primary key,
Time datetime);

create table temperatures
(Id int not null auto_increment primary key,
DeviceId int not null,
Temperature_C float,
TimeId int,
foreign key (DeviceId) references devices(Id),
foreign key (TimeId) references times(Id));

create table humiditys
(Id int not null auto_increment primary key,
DeviceId int not null,
Humidity_pct float,
TimeId int,
foreign key (DeviceId) references devices(Id),
foreign key (TimeId) references times(Id));

create table light
(Id int not null auto_increment primary key,
DeviceId int not null,
Lux int,
TimeId int,
foreign key (DeviceId) references devices(Id),
foreign key (TimeId) references times(Id));

create table radiation
(Id int not null auto_increment primary key,
DeviceId int not null,
Siverts_uSv float,
TimeId int,
foreign key (DeviceId) references devices(Id),
foreign key (TimeId) references times(Id));

delimiter $$
create procedure select_or_insert(IN inMac varchar(45), IN inDescription varchar(42))
begin
IF EXISTS (SELECT * FROM devices WHERE devices.Mac = inMac) THEN
    UPDATE devices SET Description = inDescription WHERE devices.Mac = inMac;
ELSE
    INSERT INTO devices(Mac, Description) VALUES (inMac, inDescription);
END IF;
end $$
delimiter ;

delimiter $$
create procedure add_time(IN inTime DATETIME)
begin
IF NOT EXISTS (SELECT * FROM times WHERE times.Time = inTime) THEN
    INSERT INTO times(Time) VALUES (inTime);
END IF;
end $$
delimiter ;
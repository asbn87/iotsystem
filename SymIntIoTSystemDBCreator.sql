create database symintiotsystem;
use symintiotsystem;

create table Devices
(Id int not null auto_increment primary key,
Description varchar(42),
Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

create table Times
(Id int not null auto_increment primary key,
Time datetime);

create table Temperatures
(Id int not null auto_increment primary key,
DeviceId int not null,
Temperature_C float,
TimeId int,
foreign key (DeviceId) references Devices(Id),
foreign key (TimeId) references Times(Id));

create table Humiditys
(Id int not null auto_increment primary key,
DeviceId int not null,
Humidity_pct float,
TimeId int,
foreign key (DeviceId) references Devices(Id),
foreign key (TimeId) references Times(Id));

create table Light
(Id int not null auto_increment primary key,
DeviceId int not null,
Lux int,
TimeId int,
foreign key (DeviceId) references Devices(Id),
foreign key (TimeId) references Times(Id));

create table Radiation
(Id int not null auto_increment primary key,
DeviceId int not null,
Siverts_uSv float,
TimeId int,
foreign key (DeviceId) references Devices(Id),
foreign key (TimeId) references Times(Id));


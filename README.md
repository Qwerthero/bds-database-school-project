# bds-project-assignment-3

## Fligh system database Application

## Description
In this project I implemented database design into a working application. Through the application, which has a log-in feature, you can view all passengers with their emails and some other personal information. I also implemented CRUD operations on the flight entity, which means I can create, read, update and delete flights in my database. 

I drew from my first assignment, which was the database design of a flight system, which could store data such as information about passengers, information about various flights and information about pilots and airplanes. I also drew from my second assignment, which focused on working with the database through complex queries. 

The program will simulate a flight system database where you can browse peoples account, with their email addresses. The next functionality is to work with flight, you can create, read, update and delete them. Another functionality is a log-in window, where you can log-in with email address and the right password. 

Passwords are stored in the database in hash form, the application then hashes the password, which the user entered. The two hashes are then compared and if they are not the same, then a pop-up window will appear, displaying that the user has entered the wrong password. 

The program is written in Java programming language, with the help of Apache Maven. The database is local-hosted with PostgreSQL and is accessed through PgAdmin. In the second assignment I created a no-superuser role from which I accessed the database. Its best not to use the superuser role, because if the role gets compromised, the hacker can't destroy the database in the process. 

## Installation
Make sure you have the latest version of Apache Maven.

After that, use this command to build the project (in the right directory):
```
mvn clean install
```

Then you can run the program with this command:
```
java -jar target/flight-1.0.0.jar
```

You need a working PostgreSQL database for the application to work!

## Project status
This is a finished project, although it could use some quality of life features.

## Authors and acknowledgment
Petr Bráblík - developer and designer,
Martin Hanták - designer

## License
Copyright 2022 Petr Bráblík

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.



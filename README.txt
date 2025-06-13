Matthew McCaughan
University Databases Practice Project


Welcome to my README file! This file explains how I generated my random data and how I loaded it into the database.


Generating Random Data:
	To generate the random data, I wrote a python program that would do it for me. Upon following the commands in the program, the program prints the data to add into the database in the form of SQL queries. This prorgram prints all the necessary queries for every student, and prints the commands to initialize the tables, departments, and classes. 


Loading Data:
	Loading to the data into the database is relatively rudimentary, as my program just prints out all the necessary lines to run in the database. I copy all of the lines it prints and paste them into the MySQL workspace, and run all lines. Through this, all of the data generated in my program is now in the university database. 

Included in this Zip is:

1. DataGen.py : Python program previously mentioned to generate and print the data.

2. DataBase.java : Main java JDBC application which connects to the database and allows for querying through the command terminal. It is run with three command-line parameters: Server URL, Username, and Password. URL is in the format server:port/dbname

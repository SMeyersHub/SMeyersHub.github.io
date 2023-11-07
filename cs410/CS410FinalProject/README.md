# CS410 Final Project
## Introduction
In this Programming Assignment we developed 5 programs and test cases for them. We will be using these 5 programs to learn about techniques of parallel computing. The programs that we developed are as follows:
| Part of Project        | Developed By              |
|------------------------|---------------------------|
| Java Components + Queries in Java File       | Steven Meyers             |
| SQL Tables         | Luke Davis                |


## Directory Organization
Our project directory is organized as shown below:</br>

**CS410FinalProject**</br> 
|── **model.pdf**</br>
|── **schema.sql**</br>
|── **dump.sql**</br>
|── **README.md**</br>
|── **src.zip**</br>

### Video Link
https://youtu.be/xf0wHjcTL8w

## Tools Used
### Programming Language
We programmed this assignment in SQL and used a Java shell to be able to manipulate those tables.
### Build System
We used **make** as our build tool. Steps are define to build necessary directories, compile source codes and link necessary object files to produce required executables.
 
## Usage
We will use **make** to build our assignment and run it in command-line. All the make targets are as defined in the Makefile. Some of the commands needed to build and run the assignment are as follow:

 1. #### Get rid of all binaries
```
make clean
```
 2. #### Build all source codes
 ```
make
 ```
 3. #### Test all programs
 ```
 java JavaShell
 ```
## Implementation
To make the Java and SQL work together, we used JDBC and the environment variables set on the user's machine. The JDCB automatically connects to the users set sandbox and this is where it stores all the data and creates tables. For most of the commands, a mixture of querying to gather data with restrictions and returning it back to the Java shell to manipulate before querying again to insert more data was done. Each command opens and closes all respective communications, so there is no data loss.

## Known Issues
If the user inputs a command in the correct format but with the wrong data types, such as an int where a String should go, the program will crash. No data will be lost though so the user can start the program right back up.

## Reflection
This program took significantly more time than we thought it would. Getting the Java and the SQL to work together took a lot of time and effort.


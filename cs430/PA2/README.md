# PA2
## Introduction
In this Programming Assignment we developed 5 programs and test cases for them. We will be using these 5 programs to learn about techniques of parallel computing. The programs that we developed are as follows:
| S.no. | Program                | Developed By              |
|-------|------------------------|---------------------------|
| 1.    | PI: Leibniz's Series   | Steven Meyers             |
| 2.    | PI: Monte Carlo        | Tanner Frost              |
| 3.    | Matrix-Vector Multiply | Tanner Frost |
| 4.    | Matrix-Matrix Multiply | Everest K C               |
| 5.    | Fibonacci Numbers      | Steven Meyers             | 

## Assumptions
We assumed that the amount of threads that would be allowed would be defined in the enviornment by the user, so we did not put in any limits in our code.

## Directory Organization
Our project directory is organized as shown below:</br>
**PA2**</br> 
|── **Makefile**</br>
|── **README.md**</br>
|── **bin**</br>
|── **etc**</br>
|── **include**</br>
|── **lib**</br>
|── **src**</br>
|── **test**</br>
|── **utilites**</br>

**Makefile**: This file has all the steps to build the programs, test cases and run the tests.</br>
**README.md**: This file is the main documentation of our Assignment has all the necessary description and reflection of the Assignment.</br>
**bin**: This folder contains all the final binary executables.</br>
**etc**: This folder contains all the data files. We are using this data files to run test cases. Regarding the data files, we have used **MatrixMarket I/O standard dense matrix** data file.</br>
**include**: This folder contains all the headers (**.h** files).</br>
**lib**: This folder contains all the library files (**.so** and **.o** files)  that can later be reused.</br>
**src**: This folder contains source codes (**.c** files).</br>
**test**:  This folder contains our test cases written in C (**.c** files).</br>
**utilities**: This folder contains the code base used for benchmarking. (Nothing has been committed yet to this folder.)</br>

## Tools Used
### Programming Language
We used **C** programming language to complete the given Assignment.
### Build System
We used **make** as our build tool. Steps are define to build necessary directories, compile source codes and link necessary object files to produce required executables.
 
### Matrix Library
We used [**Matrix Market I/O**](https://math.nist.gov/MatrixMarket/mmio-c.html) matrix data file specification to craft our data files and have used the **mmio** library to read the Matrix file. We have assumed that matrix file we will be using will always be dense matrix file format.

### Testing Framework
We used **Google Test** as a testing framework to write unit test and perform unit testing of our codes. We referred to the [github page](https://gist.github.com/mihaitodor/bfb8e7ad908489fdf3ceb496573f306a) to setup and integrate gtest to our make file.

## Usage
We will use **make** to build our assignment and run it in command-line. All the make targets are as defined in the Makefile. Some of the commands needed to build and run the assignment are as follow:

 1. #### Get rid of all binaries
```
make clean
```
 2. #### Build all source codes
 ```
make all
 ```
 3. #### Test all programs
 ```
 make testall
 ```
 4. #### To run programs
 All the binaries generated inside the bin folder. Before running our programs we need to set the number of threads that we want our program to spin off, so that the program runs in parallel. We will be using the environment variable **OMP_NUM_THREADS** to do so. To set the number of threads as follows:
```
export OMP_NUM_THREADS=5
 ```
 Here, 5 is the number of threads for the program to run in. We can change this number depending upon our requirement.<br/>
 After we have set he number of threads for OpenMP, we can run each program as follows:
 **PI: Leibniz's Series**
 ```
 ./bin/leibniz
 ```
 ,where a prompt will come up askign the user how far they want to run the series.
 **PI: Monte Carlo**
  ```
 ./bin/montecarlo 5
 ```
 ,where 5 is the number of decimal places up-to which we want to calculate out pi.
 **Matrix-Vector Multiply**
 ```
 ./bin/matrixvector ./etc/matrix1.mm ./etc/matrix2.mm
 ```
 where matrix1.mm and matrix2.mm are the MatrixMarket I/O matrix files for dense matrix.
 **Matrix-Matrix Multiply**
 ```
 ./bin/matrixmatrix ./etc/matrix1.mm ./etc/matrix2.mm
 ```
 where matrix1.mm and matrix2.mm are the MatrixMarket I/O matrix files for dense matrix.
 **Fibonacci Numbers**
 ```
 ./bin/fibonacci
 ``` 
,where a prompt will pop up asking the user to input the amount of times they want to run the sequence.

## Known Issues


## Reflection
We had struggled with understanding how OpenMP was handling the parallelism and how the reduction was implemented. Overall though we were overthinking and overcomplicating it. Once we understood the OpenMP parallel average assignment, then the implementation came naturally. Also, getting the Gtest suite to run was a bit of a challenge but we got it to run without warnings.


#include <stdio.h>
#include <omp.h>
#include <stdlib.h>
#include "fibonacci.h"

/* ================================================================
 Title:  CS530 Fibonacci Sequence Main
 Author: Steven Meyers
 Date:   September 10th-12th, 2022
 Description: This program carries out the Fibonacci Sequence to a
 limit.
====================================================================*/

int main(int argc, char* argv[]){
    int n;
    double start;
    double end;
    if (argc < 2)
	  {
		fprintf(stderr, "Usage: %s <number of times you want Fibonacci's sequence to go>\n", argv[0]);
		exit(1);
	  } else {
      n=atoi(argv[1]);
      start = omp_get_wtime();
      #pragma omp parallel shared(n)
      {
      #pragma omp single
      printf("Result is: %d \n", fib(n));
      }
      end = omp_get_wtime();
      printf("Work took %f seconds\n", end - start);
    }
} 

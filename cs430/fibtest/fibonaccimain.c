#include <stdio.h>
#include <omp.h>
#include "fibonacci.h"

/* ================================================================
 Title:  CS530 Fibonacci Sequence Main
 Author: Steven Meyers
 Date:   Sept 27, 2022
 Description: This program carries out the Fibonacci Sequence to a
 limit.
====================================================================*/

int main(){
    int n;
    char term;
    double start;
    double end;
    
    printf("Input the amount of times you want Fibonacci's sequence to go:");
    if(scanf("%d%c", &n, &term) != 2 || term != '\n') {
      printf("Invalid input.\n");
    } else if(n < 0){
      printf("Invalid input.\n");
    } else {
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

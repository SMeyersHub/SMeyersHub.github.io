#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include "fibonacci.h"

/* ================================================================
 Title:  CS430 Fibonacci Sequence
 Author: Steven Meyers
 Date:   September 10th-12th, 2022
 Description: This program carries out the Fibonacci Sequence to a
 limit.
====================================================================*/

int fib(int n) {
    int i, j;
    if(n < 0){
      printf("Invalid input.\n");
      return 1;
    } else if (n <= 1) {
        return n;
    }
    #pragma omp task shared(i) firstprivate(n)
    i = fib(n - 1);
    #pragma omp task shared(j) firstprivate(n)
    j = fib(n - 2);
    
    #pragma omp taskwait
    return i+j;
}
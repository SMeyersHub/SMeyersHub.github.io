#include <stdio.h>
#include <stdlib.h>
#include "leibniz.h"
#include <omp.h>

/* ================================================================
 Title:  CS430 PI: Leibniz Series Main OpenMP Implementation
 Author: Steven Meyers
 Date:   September 27th, 2022
 Description: This program carries out the Leibniz Series to a set
 number of iterations.
====================================================================*/

int main(){
    int n;
    char term;
    printf("Input how many times you want the series to run: ");
    if(scanf("%d%c", &n, &term) != 2 || term != '\n') {
      printf("Invalid input.\n");
      return 1;
    } else if(n < 0){
      printf("Invalid input.\n");
      return 1;
    } else {
      double start;
      double end;
      start = omp_get_wtime();
      double leibResult = leib(n);
      end = omp_get_wtime();
      printf("Result is: %0.*f\n", 6, leibResult);
      printf("Work took %f seconds\n", end - start);
    }
    return 0;
}

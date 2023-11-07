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

int main(int argc, char* argv[]){
    int n;
   if (argc < 2)
	  {
		fprintf(stderr, "Usage: %s <number of times you want the series to go>\n", argv[0]);
		exit(1);
	  } else {
      n=atoi(argv[1]);
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

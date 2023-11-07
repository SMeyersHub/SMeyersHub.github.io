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

double leib(int n) {
    double oddNum = 3.0;
    double result = 0;
    
    #pragma omp parallel shared(n, result)
    {
     #pragma omp for reduction( + : result )
       //Loop of the even numbers in the sequence (addition)
       for (int i = 0; i < n; i+=2 ) {
        result += 1.0 / (2 * i + 1);
      }
    #pragma omp for reduction( + : result )
      //Loop of the odd numbers in the sequence (subtract) 
      for (int i = 1; i < n; i += 2) {
        result += -1.0 / (2 * i + 1);
      }
    }
    result = result * 4;
    return result;
}


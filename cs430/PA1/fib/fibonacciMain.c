#include <stdio.h>
#include "fibonacci.h"

/* ================================================================
 Title:  CS530 Fibonacci Sequence Main
 Author: Steven Meyers
 Date:   September 10th-12th, 2022
 Description: This program carries out the Fibonacci Sequence to a
 limit.
====================================================================*/

int main(){
    int n;
    char term;
    printf("Input the amount of times you want Fibonacci's sequence to go:");
    if(scanf("%d%c", &n, &term) != 2 || term != '\n') {
      printf("Invalid input.\n");
    } else if(n < 0){
      printf("Invalid input.\n");
    } else {
      int fibResult = fib(n);
      printf("Result is: %d \n", fibResult);
    }
}
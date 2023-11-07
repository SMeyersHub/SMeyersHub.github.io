#include <stdio.h>
#include <stdlib.h>
#include "leibniz.h"

/* ================================================================
 Title:  CS430 PI: Leibniz Series Main
 Author: Steven Meyers
 Date:   September 10th-12th, 2022
 Description: This program carries out the Leibniz Series to a set
 limit.
====================================================================*/

int main(){
    int n;
    char term;
    printf("Input the amount of times you want the series to go for (ex: '2' is 1 - (1/3) + (1/5)): ");
    if(scanf("%d%c", &n, &term) != 2 || term != '\n') {
      printf("Invalid input.\n");
    } else if(n < 0){
      printf("Invalid input.\n");
    } else {
      float lebResult = leib(n);
      lebResult = lebResult * 4;
      printf("Result is: %f \n", lebResult);
    }
}
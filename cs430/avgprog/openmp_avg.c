#include <stdio.h>
#include <omp.h>

#define SIZE 1000

/*
  Author: Steven Meyers
  Date: 9/21/2022
  Class: CS430
  Purpose: This file calculates the avg of SIZE. It does this using OpenMP.
*/

int arr[SIZE];
int main() {

  for(int i = 0; i <SIZE; i++) {
    arr[i] = i;
  }
  float avg, sum=0; int i;
  #pragma omp parallel for reduction(+:sum)
  for(int i = 0; i < SIZE; i++) {
    sum = sum + arr[i];
  }
  
  float total_sum = sum/SIZE;
  
  printf("Sum is: %f\n", total_sum);
}
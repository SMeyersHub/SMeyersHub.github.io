#include <stdio.h>
#include <pthread.h>

#define SIZE 1000
#define THREAD_NUM 5

/*
  Author: Steven Meyers
  Date: 9/13/2022
  Class: CS430
  Purpose: This file calculates the avg of SIZE. It does this both using a serial and parallel way.
*/
int arr[SIZE];
float thread_sum[THREAD_NUM] = {0};
int counter = 0;

void* add_thread(void* arg) {

  int threadcounter = counter++;
  
  //threadcounter keeps track of the start and stop points based on how big the size is 
  //and how many parts it is divided into.
  for (int i = threadcounter *(SIZE /THREAD_NUM); i < (threadcounter + 1) *(SIZE/THREAD_NUM); i++) {
    thread_sum[threadcounter] += arr[i];
  }
}

int main() {
  //Begin Serial Computation
  float s_result;
  for(int i = 0; i < SIZE; i++) {
    s_result = s_result + i;
    //fill the array for the parallel threading part.
    arr[i] = i;
  }
  s_result = s_result/SIZE;
  printf("Serial Computation result: %f\n", s_result);
  
  //Begin Parallel Threading
  pthread_t thread[THREAD_NUM-1];
  
  for(int i = 0; i < THREAD_NUM; i++) {
    pthread_create(&thread[i], NULL, add_thread, (void*)NULL);
  }
  
  for(int i = 0; i < THREAD_NUM; i++) {
    pthread_join(thread[i], NULL);
  }
  
  float p_result = 0;
  for(int i = 0; i < THREAD_NUM; i++) {
    p_result = p_result + thread_sum[i];
  }
  
  p_result = p_result/SIZE;
  printf("Parallel Computation result: %f\n", p_result);
}
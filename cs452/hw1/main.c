#include <stdio.h>
#include <stdlib.h>

#include "deq.h"

/**
* Tests the put function of the deq
* Deq q- queue with the data being put in
* Returns - 0 if no error is thrown.
*/
int test_put(Deq q) {
  char* var ="a";
  char* var2="b";
  char* var3="c";
  deq_head_put(q, var);  //a
  deq_head_put(q, var2); //ba
  deq_tail_put(q, var);  //baa
  deq_tail_put(q, var2); //baab
  deq_tail_put(q, var3); //baabc
  deq_tail_put(q, var3); //baabcc
  return 0;
}

/**
* Tests the put function of the deq
* Deq q- queue with the data being got out
* Returns - 0 if no error is thrown.
*/
int test_get(Deq q) {
  deq_head_get(q); 
  deq_tail_get(q);
  return 0;
}

/**
* Tests the ith function of the deq
* Deq q- queue with the data being put in
* int i- number of nodes being moved from end
* Returns - 0 if no error is thrown.
*/
int test_ith(Deq q, int i) {
  deq_head_ith(q, i);
  deq_tail_ith(q, i);
  return 0;
}

/**
* Tests the rem function of the deq
* Deq q- queue with the data being put in
* Data d- data being searched for and removed.
* Returns - 0 if no error is thrown.
*/
int test_rem(Deq q, Data d) {
  deq_head_rem(q, d);
  deq_tail_rem(q, d);
  return 0;
}

/**
* Walks through the tests above
* Deq q- queue with the data being put in
* Returns - 0 if no error is thrown.
*/
int test(Deq q) {

  char *var = "a";
  char *var2 = "d";
  test_put(q); //baabcc
  test_get(q); //aabc
  test_ith(q, 1); //a and c
  test_rem(q, var);  //bc
  test_rem(q, var2); //no data found

  return 0;
}

int main() {
  Deq q=deq_new();

  test(q); //Runs the tests for the deq.
  
  printf("%d\n",deq_len(q));
  
  char *s=deq_str(q,0);
  printf("%s\n",s);
  free(s);
  
  deq_del(q,0);
  return 0;
}

#include <stdio.h>
#include <stdlib.h>
#include "fibonacci.h"

/* ================================================================
 Title:  CS430 Fibonacci Sequence
 Author: Steven Meyers
 Date:   September 10th-12th, 2022
 Description: This program carries out the Fibonacci Sequence to a
 limit.
====================================================================*/

int fib(int n) {
    if (n < 0) {
        return 1;
    }
    if (n <= 1) {
        return n;
    }

    int result = fib(n - 1) + fib(n - 2);

    return result;
}
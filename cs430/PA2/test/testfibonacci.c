
/* ================================================================
 Title:  CS430 P1 Fibonacci Sequence
 Author: Steven Meyers
 Date:   September 10th-12th, 2022
 Description: 
 This program tests the Fibonacci Sequence program. 
====================================================================*/
#include "gtest/gtest.h"

extern "C" {
  #include "fibonacci.h"
}

TEST(fibonacciTest, HandlesZeroInput) {
  ASSERT_EQ(fib(0), 0);
}

TEST(fibonacciTest, HandlesNegativeValues) {
  ASSERT_EQ(fib(-1), 1);
}

TEST(fibonacciTest, HandlesPositiveValues) {
  ASSERT_EQ(fib(1), 1);
  ASSERT_EQ(fib(2), 1);
  ASSERT_EQ(fib(3), 2);
  ASSERT_EQ(fib(4), 3);
  ASSERT_EQ(fib(5), 5);
  ASSERT_EQ(fib(6), 8);
  ASSERT_EQ(fib(30), 832040);
}
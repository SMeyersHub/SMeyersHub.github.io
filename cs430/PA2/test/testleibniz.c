
/* ================================================================
 Title:  CS430 P1 Leibniz Series Test
 Author: Steven Meyers
 Date:   September 10th-12th, 2022
 Description: 
 This program tests the Leibniz Series program. 
====================================================================*/
#include "gtest/gtest.h"

extern "C" {
  #include "leibniz.h"
}

TEST(leibnizTest, HandlesZeroInput) {
  ASSERT_EQ(leib(0), 0.000000);
}

TEST(leibnizTest, HandlesNegativeValues) {
  ASSERT_EQ(leib(-1), 1);
}

TEST(leibnizTest, HandlesPositiveValues) {
  ASSERT_NEAR(leib(1), 4.000000, 0.000001);
  ASSERT_NEAR(leib(10), 3.041840, 0.000001);
  ASSERT_NEAR(leib(20),  3.091624, 0.000001);
  ASSERT_NEAR(leib(30), 3.108269, 0.000001);
  ASSERT_NEAR(leib(100), 3.131593, 0.000001);
  ASSERT_NEAR(leib(1000), 3.140593, 0.000001);
  ASSERT_NEAR(leib(100000), 3.141583, 0.000001);
  ASSERT_NEAR(leib(100000000), 3.141593, 0.000001);
}
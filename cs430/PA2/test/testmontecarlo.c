#include "gtest/gtest.h"

extern "C" {
#include "montecarlo.h"
}

TEST(inputInRange, not_ok){
  char input[]="11111111111";
  EXPECT_EQ(monteCarlo(input),1);
}

TEST(inputInRange, ok){
  char input[]="11";
  EXPECT_NE(monteCarlo(input),0);   
}

TEST(MontecarloCalc1, ok){
  char input[]="10000";
  EXPECT_NEAR(monteCarlo(input),3.141592,1.0);
}

TEST(MontecarloCalc2, ok){
  char input[]="100000";
  EXPECT_NEAR(monteCarlo(input),3.141592,0.1);
}

TEST(MontecarloCalc3, ok){
  char input[]="1000000";
  EXPECT_NEAR(monteCarlo(input),3.141592,0.01);
}
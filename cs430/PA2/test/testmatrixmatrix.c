
/* ================================================================
 Title:  CS530 P1 Matrix Matrix Multiplication
 Author: Everest K C
 Date:   September 10th-12th, 2022
 Description: 
 This program tests the Matrix Multiplicaiton progam. 
====================================================================*/
#include "gtest/gtest.h"
#include <omp.h>

extern "C" {
#include "matrixMatrix.h"
}

TEST(fileExistsTest, ok) {
  char fname[]="./etc/matrix1.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),0);
}

TEST(fileExistsTest, not_ok) {
  char fname[]="./etc/jpt_matrix.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),1);
}

TEST(readBannerTest, ok) {
  char fname[]="./etc/matrix1.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),0);
}

TEST(readBannerTest, not_ok) {
  char fname[]="./etc/invalidmatrix.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),1);
}

TEST(isMatrixTest, ok){
  char fname[]="./etc/matrix1.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),0);
}

TEST(isMatrixTest, not_ok){
  char fname[]="./etc/invalidmatrix.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),1);
}

TEST(readMatrixTest, ok) {
  int ntArrayRows=2,ntArrayColumns=3;
  double tArray[]={2,1,4,0,1,1};
  char fname[]="./etc/matrix1.mm";
  char *filename=fname;
  double *A;
  int i=0;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),0);
  EXPECT_EQ(nRowA,ntArrayRows);
  EXPECT_EQ(nColumnA,ntArrayColumns);
  for (i=0;i<ntArrayRows*ntArrayColumns;i++){
    EXPECT_NEAR(tArray[i],A[i],0.0001);
  }
}

TEST(checkMatrixSizeTest, ok) {
  EXPECT_EQ(checkMatrix(1, 1),0);
}

TEST(checkMatrixSizeTest, not_ok) {
  EXPECT_EQ(checkMatrix(1, 2),1);
}

TEST(multiplyMatrixTest, ok) {
  int i=0;
  int nRowA=2,nColumnA=3;
  double A[]={2,1,4,0,1,1};

  int nColumnB=4;
  double B[]={6,3,-1,0,1,1,0,4,-2,5,0,2};
  
  double result[]={5,27,-2,12,-1,6,0,6};
  double *product=multiplyMatrix(A,nRowA,nColumnA,B,nColumnB);

  for (i=0;i<nRowA*nColumnB;i++){
    EXPECT_NEAR(result[i],product[i],0.0001);
  }
}







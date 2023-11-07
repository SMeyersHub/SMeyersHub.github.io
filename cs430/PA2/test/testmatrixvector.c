
#include "gtest/gtest.h"

extern "C" {
#include "matrixVector.h"
}

TEST(fileExistsVectorTest, ok) {
  char fname[]="./etc/matrix1.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),0);
}

TEST(fileExistsVectorTest, not_ok) {
  char fname[]="./etc/jpt_matrix.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),1);
}

TEST(readBannerVectorTest, ok) {
  char fname[]="./etc/matrix1.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),0);
}

TEST(readBannerVectorTest, not_ok) {
  char fname[]="./etc/invalidmatrix.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),1);
}

TEST(isMatrixVectorTest, ok){
  char fname[]="./etc/matrix1.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),0);
}

TEST(isMatrixVectorTest, not_ok){
  char fname[]="./etc/invalidmatrix.mm";
  char *filename=fname;
  double *A;
  int nRowA,nColumnA;
  EXPECT_EQ(readMatrix(filename,&A,&nRowA,&nColumnA),1);
}

TEST(readMatrixVectorTest, ok) {
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

TEST(checkMatrixVectorSizeTest, ok) {
  EXPECT_EQ(checkMatrix(1, 1),0);
}

TEST(checkMatrixVectorSizeTest, not_ok) {
  EXPECT_EQ(checkMatrix(1, 2),1);
}

TEST(multiplyMatrixVectorTest1, ok) {
  int i=0;
  int nRowA=3,nColumnA=3;
  double A[]={2,1,4,0,1,1,5,7,9};

  int nColumnB=1;
  double B[]={6,3,-1};
  
  double result[]={11,2,42};
  double *product=multiplyMatrix(A,nRowA,nColumnA,B,nColumnB);

  for (i=0;i<nRowA*nColumnB;i++){
    EXPECT_NEAR(result[i],product[i],0.0001);
  }
}



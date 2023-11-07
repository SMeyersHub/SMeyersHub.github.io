#ifndef MATRIX_VECTOR
#define MATRIX_VECTOR

int readMatrix(char *filename,  double ** J,int *row,int *column);
int checkMatrix(int nColumnA, int nRowB);
double* multiplyMatrix(double *A,int nRowA,int nColumnA,double *B, int nColumnB);

#endif
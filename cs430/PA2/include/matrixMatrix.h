#ifndef MATRIX_MATRIX
#define MATRIX_MATRIX

int readMatrix(char *filename,  double ** J,int *row,int *column);
int checkMatrix(int nColumnA, int nRowB);
double* multiplyMatrix(double *A,int nRowA,int nColumnA,double *B, int nColumnB);

#endif
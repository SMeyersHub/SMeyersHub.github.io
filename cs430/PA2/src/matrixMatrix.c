/* ================================================================
 Title:  CS530 P1 Matrix Matrix Multiplication
 Author: Everest K C
 Date:   September 27th-28th, 2022
 Description: 
 This program reads two dense matrices from given MatrixMarket file
 calculates the product in parallel and displays the output. 
====================================================================
*/
#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include "mmio.h"
#include "matrixMatrix.h"

/* reads the dense matrix from MatrixMarket I/O file and returns 0 in success */
int readMatrix(char *filename,  double **J, int *row,int *column){
    int i;
    int size=0;
    FILE *f;
    MM_typecode matcode;

    if ((f = fopen(filename, "r"))== NULL){
        printf("Could not read file.\n");
        return 1;
    }

    /* read and process the MatrixMarket file banner */
    if (mm_read_banner(f, &matcode) != 0)
    {
        printf("Could not process Matrix Market banner.\n");
        return 1;
    }

    /* check if the matrix is dense matrix or not */
    if (mm_is_matrix(matcode) && ! mm_is_dense(matcode))
    {
        printf("Sorry, this application does not support ");
        printf("Market Market type: [%s]\n", mm_typecode_to_str(matcode));
        return 1;
    }

    /* find the size of the dense matrix */
    if ((mm_read_mtx_array_size(f, row, column)) !=0)
        return 1;
    
    /* reserve memory for matrix */
    size=(*row) * (*column);
    *J = (double *) malloc(size * sizeof(double));

    /* read from file into an array */
    for (i=0; i<size; i++)
    {
        fscanf(f, "%lg\n", &(*J)[i]);
    }
    if (f !=stdin) fclose(f);
    return 0;
}

/* checks if the matrices can be multiplited */
int checkMatrix(int nColumnA, int nRowB){
    if(nColumnA==nRowB){
        return 0;
    }
    else{
        return 1;
    } 
}

/* perform matrix multiplication */
double* multiplyMatrix(double *A,int nRowA,int nColumnA,double *B, int nColumnB){
    int i,j,k;
    int resultingSize;
    //int index=0;
    //int result;
    resultingSize=nRowA*nColumnB;
    double *C=(double*)malloc(resultingSize * sizeof(double));
    /* openmp pragma to parallelize the matrix multiplication */
    #pragma omp parallel private(i,j,k) shared(A,B,C)
    {
        /* loop through the rows of matrix A */
        for(i=0;i<nRowA;i++){
            /* loop through the columns of matrix B */
            for(j=0;j<nColumnB;j++){
                C[i*nColumnB+j]=0;
                /* loop through the elements of matrices */
                for(k=0;k<nColumnA;k++){
                    C[i*nColumnB+j]+=A[i*nColumnA+k]*B[j+k*nColumnB];
                }
            }
        }
    }
    return C;
}



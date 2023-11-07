/* ================================================================
 Title:  CS530 P1 Matrix Matrix Multiplication
 Author: Everest K C
 Date:   September 10th-12th, 2022
 Description: 
 This program reads two dense matrices from given MatrixMarket file
 calculates the product and displays the output. 
====================================================================*/
#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include "matrixMatrix.h"

/* main function reads name of MartixMarket file from the commandline input and then 
calls functions to read the matrices, check the matrices and calculate its product */
int main(int argc, char* argv[]){
    double *A, *B, *C;
    int nRowA, nColumnA, nRowB, nColumnB;
    int i,resultingSize;
    if (argc < 3)
	{
		fprintf(stderr, "Usage: %s [martix-market-filename1] [martix-market-filename2]\n", argv[0]);
		exit(1);
	}else{
        if(readMatrix(argv[1],&A,&nRowA,&nColumnA)!=0){
            exit(1);
        }
        if(readMatrix(argv[2],&B,&nRowB,&nColumnB)!=0){
            exit(1);
        }
    }

    if(checkMatrix(nColumnA,nRowB)==0){
        double start = omp_get_wtime();
        C=multiplyMatrix(A,nRowA,nColumnA,B,nColumnB);
        double end = omp_get_wtime();
        printf("Work took %f seconds\n", end - start);
        
    }
    else{
        printf("Number of columns of first matrix doesn't match the number of rows of second matrix.\n");
        exit(1);
    }
    resultingSize=nRowA*nColumnB;

    for(i=0;i<=resultingSize-1;i++){
        printf("%lf\n",C[i]);
    }

    free(A);
    free(B);
    return 0;
}
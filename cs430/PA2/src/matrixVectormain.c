#include <stdio.h>
#include <stdlib.h>
#include "matrixVector.h"

/* main function reads name of MartixMarket file from the commandline input and then 
calls functions to read the matrices, check the matrices and calculate its product */
int main(int argc, char* argv[]){
    double *A, *B, *C;
    int nRowA, nColumnA, nRowB, nColumnB;
    int resultingSize;

    if (argc != 3)
	{
		printf("\n Error1: Invalid argument count. Enter only two .mm matrices. ");
		exit(1);
	}else{
        if(readMatrix(argv[1],&A,&nRowA,&nColumnA)!=0){
            exit(1);
        }
        if(readMatrix(argv[2],&B,&nRowB,&nColumnB)!=0){
            exit(1);
        }
    }

	// check for incompatible col to row size
	if(nColumnA!=nRowB){
		printf("\n Incompatible matrix sizes \n");
		exit(1);
	}
	
	// check that B is  [nx1] column vector for Matrix-Vector multiply
	if( nColumnB!=1){
		printf("\n Second input matrix must be [nx1] column vector");
		exit(1);
	}
	
	// if no exits so far, proceed 
	resultingSize=nRowA;
	
	C=multiplyMatrix(A,nRowA,nColumnA,B,nColumnB);


    for(int i=0;i<=resultingSize-1;i++){
        printf("%lf\n",C[i]);
    }

    free(A);
    free(B);
    return 0;
}
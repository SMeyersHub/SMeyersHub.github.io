#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <sys/time.h>
#include "mmio.h"
#include "matrixVector.h"

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
    int resultingSize;
	nColumnB+=0;
    resultingSize= nRowA;
    double *C=(double*)malloc(resultingSize * sizeof(double));

	struct timeval stop, start;
	gettimeofday(&start, NULL);
    #pragma omp parallel for
	for(int curr=0;curr<nRowA;curr++){
		for(int i=0;i<nColumnA;i++){
			C[curr]+=A[curr*nColumnA + i]*B[i];
		}
	}
	gettimeofday(&stop, NULL);
	printf("%lu us\n", (stop.tv_sec - start.tv_sec) * 1000000 + stop.tv_usec - start.tv_usec);
    return C;
}


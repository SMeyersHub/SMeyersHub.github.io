/* ==========================================
 Title:  CS430 P2 Monte Carlo
 Author: Tanner Frost
 Date:   September 28th, 2022
 Description: 
 Using the MonteCarlo method, take a circle with radius 
 r=1. Then randomize points X,Y in [0,1]. Count the hits 
 that fall within the circle as a proportion. Use the 
 area of a circle formula to approximate pi. Needs 
 more than 100,000 points for 2 decimal place precision. 
 Let OpenMp dictate number of threads, and split the
 randomizing between them. Use reduction on count to 
 sum all the parallelized counts. 
 Timing adapted from here: https://stackoverflow.com/a/10192994
==========================================
*/
#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <math.h>
#include <time.h>
#include <sys/time.h>

double monteCarlo(char *argv){
    double x,y,pi;
	double count =0;
	int nSlices= atoi(argv); // atoi library parses string to int
	struct timeval stop, start;
	

	if(nSlices<1){   // Checking input range. There are a few other ways to do this check here. 
		printf("\n Error2: Invalid range of input int. Retry with 1 to int range.");
		return 1;
	}
    
	gettimeofday(&start, NULL);
	srand(time(NULL)); // pseudorandomize using sys clock. 
	#pragma omp parallel for reduction(+ : count)
	for(int i=0;i<nSlices;i++){
        x = (double) rand() / (double)RAND_MAX ;
        y = (double) rand() / (double)RAND_MAX  ;
        if(	(x*x+y*y) < 1.0) // pythag theorem with r=1 saves a sqrt() operation. 
            count++;
	}
	gettimeofday(&stop, NULL);
	printf("%lu us\n", (stop.tv_sec - start.tv_sec) * 1000000 + stop.tv_usec - start.tv_usec);
	pi = 4*count/nSlices;
    return pi;
}
#include <stdio.h>
#include "montecarlo.h"

int main(int argc,char *argv[])
{
    double pi=0.0;
    if(argc!=2){
		printf("\n Error1: Invalid syntax. Enter only one integer cmd argument for # of points.");
	}else{
        pi=monteCarlo(argv[1]);
    }
	
	printf("Pi = %lf\n",pi);
	return 0;
}
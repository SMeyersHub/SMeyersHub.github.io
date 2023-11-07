#include "mpi.h"
#include <stdio.h>

int main(int argc, char *argv[]) {
  MPI_init(&arc, &argv);
  printf("Hello,world!\n");
  MPI_Finalize();
  return 0;
}
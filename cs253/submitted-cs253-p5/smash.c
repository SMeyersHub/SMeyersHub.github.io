#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "smash.h"
#include "history.h"
#define MAXLINE 4096

int main() {

init_history();

char bfr[MAXLINE];
fputs("$",stderr);

while(fgets(bfr, MAXLINE, stdin) != NULL) {
	bfr[strlen(bfr) - 1] = '\0';
	executeCommand(bfr);
	fputs("$",stderr);
}
clear_history();
return 0;
}

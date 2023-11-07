#include "smash.h"
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include "history.h"

//Program to execute commands in the smash program.
//Author: Steven Meyers
//Date: 8/9

void executeCommand(char *str) {
	int i = 0, n = 20;
	
	char exitArr[5] = "exit";
	char *exitPtr = exitArr;
	char cdArr[3] = "cd";
	char *cdPtr = cdArr;
	char historyArr[8] = "history";
	char *histPtr = historyArr;
	
	char *strPtr = (char *) malloc(strlen(str) + 1);
	strncpy(strPtr, str, strlen(str));

	char *args[n];

	char *nextWord = strtok(str, " ");
	while(nextWord != NULL) {
		args[i] = nextWord;
		nextWord = strtok(NULL, " ");
		i++;
	}
	args[i] = NULL;
	
	if(args[0] == NULL) {
		free(strPtr);
		clear_history();
		return exit(0);
	}
	//cd command
	if(strncmp(args[0], cdPtr, strlen(cdPtr)) == 0) {
		char cDir[100];

		if(chdir(args[1]) == 0) {
			add_history(strPtr, 0);
			fprintf(stdout, "%s\n", getcwd(cDir, 100));
		} else if (chdir(args[1]) == -1) {
			add_history(strPtr,1);
			fprintf(stderr, "%s: No such file or directory\n", args[1]);
		}

 	//history
	} else if(strncmp(args[0], histPtr, strlen(histPtr)) == 0) {
		if(i > 10) {
			print_history(i -10);
		} else {
			print_history(i);
		}
	add_history(strPtr, 0);

	//exit
	} else if(strncmp(args[0], exitPtr, strlen(exitPtr)) == 0) {
		free(strPtr);
		clear_history();
		return exit(0);

	} else {
		add_history(strPtr, 127);
		for(int n = 0; n < i; n++) {
			fprintf(stdout, "[%d] %s\n", n, args[n]);
		}
	}
	free(strPtr);
}

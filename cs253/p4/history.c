#include "history.h"
#include <stdlib.h>
#include <stdio.h>

char test[5] = "test";
int i = 0;
struct Cmd **cmdHistory;
void init_history(void) {
	cmdHistory = malloc(10 * sizeof(struct Cmd*));
	for(int n = 0; n < 10; n++) {
		cmdHistory[n] = malloc(sizeof(struct Cmd));
		cmdHistory[n]->cmd = NULL;
		cmdHistory[n]->exitStatus = 0;
	}
}

void add_history(char *cmd, int exitStatus) {
	
	if(i < 10) {
		cmdHistory[i]->cmd = cmd;
		cmdHistory[i]->exitStatus = exitStatus;
		i++;
	} else if(i > 9) {
		i = 0;
		free(cmdHistory[i]->cmd);
		cmdHistory[i] = malloc(sizeof(struct Cmd));
		cmdHistory[i]->cmd = cmd;
		i++;
		//shiftdown
		/*for(int n = 0; n < 10; n++) {
			free(cmdHistory[n]->cmd);
			cmdHistory[n] = malloc(sizeof(struct Cmd));
			cmdHistory[n]->cmd = cmdHistory[n+1]->cmd;
			cmdHistory[n]->exitStatus = cmdHistory[n+1]->exitStatus;
		}
		free(cmdHistory[10]->cmd);
		cmdHistory[10] = malloc(sizeof(struct Cmd));
		cmdHistory[10]->cmd = cmd;
		cmdHistory[10]->exitStatus = exitStatus;*/
	}
}

void clear_history(void) {
	for(int n = 0; n < 10; n++) {
		free(cmdHistory[n]->cmd);
	}
	free(cmdHistory);
}

void print_history(int firstSequenceNumber) {
	for(int n = 0; n < 10; n++) {
		if(cmdHistory[n]->cmd != NULL) {
			fprintf(stdout, "%d [%d] %s\n", firstSequenceNumber, cmdHistory[n]->exitStatus, cmdHistory[n]->cmd);
		}
		firstSequenceNumber++;
	}
}

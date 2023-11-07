#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int doDelete(char *line1, char *line2) {
	char line[1023];
	int copyTrigger = 1;
	int intOne = atoi(line1);
	int intTwo = atoi(line2);
	int lineCount = 1;
	
	//if line one is smaller than two, return invalid return
	if(intOne >= intTwo || intOne <= 0) {
		return 1;
	}
	//fputs the line until line1 is hit, then stop till line 2 is hit
	while(fgets(line, sizeof(line), stdin) != '\0') {
		if(lineCount == intOne) {
			copyTrigger = 0;
		}
		if(copyTrigger ==1) {
			fputs(line, stdout);
		}
		if(lineCount == intTwo) {
			copyTrigger = 1;
		}
		lineCount++;
	}
return 0;
}




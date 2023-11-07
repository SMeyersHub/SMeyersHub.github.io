#include <stdio.h>
#include <stdlib.h>

int doCopy() {
	//gets the line and puts it in stdout
	char line[1023];
	while(fgets(line,sizeof(line), stdin) != '\0') {
		fputs(line, stdout);
	}
return 0;
}

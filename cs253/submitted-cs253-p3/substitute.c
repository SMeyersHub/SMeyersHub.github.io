#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//Pattern is what is being replaced (old value), string is new value.
int doSubstitute(char *pattern, char *string) {
	char line[1023];
	char *pos, temp[1023];
	int index = 0;
	//length of old word
	int owlen;

	owlen = strlen(pattern);
	
	//go through line by line, and search for pattern, if pattern
	//is found then cut it with a \0, and cat the word in center
	//then the other half back on.
	while(fgets(line, sizeof(line),stdin) != '\0') {
		while((pos = strstr(line, pattern)) != NULL) {
			strncpy(temp, line, sizeof(line));

			index = pos - line;

			line[index] = '\0';

			strcat(line, string);

			strcat(line, temp + index + owlen);
		}
		fputs(line, stdout);
	}
  return 0;
}

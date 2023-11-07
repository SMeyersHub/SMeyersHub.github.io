//-----------------------------------------------------------------------------
//
// NAME
//  sed253 -- Simplified editor
//
// SYNOPSIS
//  sed253
//  sed253 -s pattern string
//  sed253 -d line1 line2
//
// DESCRIPTION
//  Simplified editor.  Copies lines read from stdin to stdout.  Options:
//
//  -s Substitute every occurrence of pattern with string
//  -d Delete line1 through line2 inclusive
//
// ERRORS
//  Prints usage message and exits abnormally for invalid commands.  Prints an
//  error message and exits abnormally for other issues.
//
// LIMITATIONS
//  Lines of text are limited to a maximum of 1023 chars.
//
// AUTHORS
//  Epoch...................................................................jrc
//
//-----------------------------------------------------------------------------

//Bring in the definitions for our helper functions
#include "copy.h"
#include "substitute.h"
#include "delete.h"

//-----------------------------------------------------------------------------
// main -- the main function
//-----------------------------------------------------------------------------

//bring in these to prevent the warnings when running program.
#include <stdio.h>
#include <string.h>

int main(int argc, char **argv) {
	char *pattern;
	char *string;
	char *lineOne;
	char *lineTwo;
	
	//return state of 0 if everything goes correct
	int returnState = 0;

	//for copy function
	if(argc==1) {
		doCopy();
	}
	//for both other functions
	else if(argc==4) {
		//substitute function
		if(strncmp(argv[1], "-s", 2) == 0) {
			pattern = argv[2];
			string = argv[3];
			doSubstitute(pattern, string);
		}
		//delete function
		else if(strncmp(argv[1], "-d", 2) == 0) {
			lineOne = argv[2];
			lineTwo = argv[3];
			doDelete(lineOne, lineTwo);		
		}
		//print err if -s or -d is not present
		else {
			returnState = 1;
			char errMsg[52] = "usage:  sed253 [-s pattern string] [-d line1 line2]";
			printf("%s \n", errMsg);
		}
	}
	//print err msg if argc is not correct
	else {
		returnState = 1;
		char errMsg[52] = "usage:  sed253 [-s pattern string] [-d line1 line2]";
		printf("%s \n", errMsg);
		
	}
return returnState;
}




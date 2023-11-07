//Include definitions for C Runtime Library functions used in this program
#include <stdio.h>				//The standard I/O functions

//-------------------------------------------------------------------------------
//This is the main function, invoked by the C Runtime (CRT) to start the program
//-------------------------------------------------------------------------------
int main(void) {
	int uppercase = 0;
	int lowercase = 0;
	int vowel = 0;
	int consonant = 0;
	int digit = 0;
	int total = 0;
	int c;
	while((c=getchar()) != EOF) {
		++total;
		if(c >= 48 && c <= 57){
			++digit;
		}
		else if(c >= 65 && c <= 90){
			++uppercase;
			if(c == 65 || c == 69 || c == 73 || c == 79 || c == 85 || c == 89){
				++vowel;
			} else {
				++consonant;
			}
		}
		else if(c >= 97 && c <= 122){ 
			++lowercase;
			if(c == 97 || c == 101 || c == 105 || c == 111 || c == 117 || c == 121 ){
				++vowel;
			}
			else{
				++consonant;
			}
		}
	}
	printf("upper-case: %d\n", uppercase);
	printf("lower-case: %d\n", lowercase);
	printf("vowels: %d\n", vowel);
	printf("consonants: %d\n", consonant);
	printf("digits: %d\n", digit);
	printf("total: %d\n", total);
	return(0);
}
	

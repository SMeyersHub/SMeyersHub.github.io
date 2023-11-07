#include <stdio.h>
#include <stdlib.h>
#include "leibniz.h"

/* ================================================================
 Title:  CS430 PI: Leibniz Series
 Author: Steven Meyers
 Date:   September 10th-12th, 2022
 Description: This program carries out the Leibniz Series to a set
 limit.
====================================================================*/

float leib(int n) {
    float oddNum = 3.0;
    float result = 1.0;
    int signSwitch = 0; //0 is negative, 1 is positive
    while(n > 0) {
        if (signSwitch == 0) {
            result = result - (1/oddNum);
            oddNum = oddNum + 2;
            signSwitch = 1;
        } else if (signSwitch == 1) {
            result = result + (1/oddNum);
            oddNum = oddNum + 2;
            signSwitch = 0;
        }
        n--;
    }
    return result;
}


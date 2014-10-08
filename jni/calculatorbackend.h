/*
 * CalculatorBackend.h
 *
 *  Created on: 25-Sep-2014
 *      Author: dev
 */

#include <jni.h>


#ifndef CALCULATORBACKEND_H_
#define CALCULATORBACKEND_H_

#ifndef NULL
#define NULL   ((void *) 0)
#endif


double getTheValueAfterCalculation(char* stringContaingNumbersAndOperators);
double tempgetDoubleValue();

#endif


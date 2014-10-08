/*
 * fastoperations.c
 *
 *  Created on: 25-Sep-2014
 *      Author: dev
 */

#include <jni.h>
#include "fastoperations.h"
#include "calculatorbackend.h"


jdouble Java_com_learnz_ndkcalculator_act_CalcUI_calculate(JNIEnv* env,
        jobject obj,jstring stringContaingNumbersAndOperators){

	char* stringConvertedToChar;
	stringConvertedToChar=(*env)->GetStringUTFChars(env,stringContaingNumbersAndOperators,((void *) 0));


//	getTheValueAfterCalculation((*env)->GetStringUTFChars(env,stringContaingNumbersAndOperators,((void *) 0)));

//	tempgetDoubleValue();

	return getTheValueAfterCalculation(stringConvertedToChar);

}

jint Java_com_learnz_ndkcalculator_act_CalcUI_display(JNIEnv* env,
        jobject obj){
//	printf("hello word inside native function");
	return 1;

}



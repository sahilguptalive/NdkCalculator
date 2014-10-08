/*
 * fastoperations.h
 *
 *  Created on: 25-Sep-2014
 *      Author: dev
 */

#include <jni.h>

#ifndef FASTOPERATIONS_H_
#define FASTOPERATIONS_H_

#ifndef NULL
#define NULL   ((void *) 0)
#endif


jdouble Java_com_learnz_ndkcalculator_act_CalcUI_calculate(JNIEnv*,
        jobject,jstring);

jint Java_com_learnz_ndkcalculator_act_CalcUI_display(JNIEnv* env,
        jobject obj);

#endif /* FASTOPERATIONS_H_ */

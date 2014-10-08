/*
 ============================================================================
 Name        : CalculatorBackend.c
 Author      :
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include "calculatorbackend.h"



#define TRUE 1
#define FALSE 0
#define INVALID -1



struct Calc {

	int isOperation;
	double valueToParse;
	struct Calc* next;

};

//char operationsToLookFor[] = { '+', '-', '*', '/', '=' };

void generateStackFromStringToCalculate(char* stringToCalculate, struct Calc* start);

int isCharParsedAOperation(char character);

int getIntFromChar(char charToGetIntFrom);

void printCreatedStack(struct Calc*);

void calculateValueFromStackCreated(struct Calc*);

double getTheValueAfterCalculation(char* stringContaingNumbersAndOperators) {

	struct Calc* startPtr;

	startPtr = (struct Calc*) malloc(sizeof(struct Calc));

	startPtr->next = NULL;

	//we need to ensure that string to calculate ends with '='
	//in other words we need ensure that each number is followed by a operator
	//which can be + or - or * or / or =
	const char* stringToCalculate = stringContaingNumbersAndOperators;//"23/97*40-41-21+34*58=";	//"10/100*50*3=";//"40-21*58";

	generateStackFromStringToCalculate(stringToCalculate, startPtr);

	printf("\n ============== Stack Created ================ ");

	printCreatedStack(startPtr);

	calculateValueFromStackCreated(startPtr);

	return startPtr->next->valueToParse;

}

/**
 * will return 1 if character is any  operation from + or - or * or / or =
 *
 * else will return 0
 */
int isCharParsedAOperation(char character) {
	int isOperation = 0;
	switch (character) {
	case '+':
	case '-':
	case '*':
	case '/':
	case '=':
		isOperation = 1;
		break;
	default:
		break;
	}
	return isOperation;

}

int getIntFromChar(char charToGetIntFrom) {
	int intToReturn = charToGetIntFrom - '0';
	return intToReturn;

}

void generateStackFromStringToCalculate(char* stringToCalculate, struct Calc* start) {
	int index;
	struct Calc* prev = start;

	if (stringToCalculate == NULL ) {

		printf("we got an empty string");

	} else if (start == NULL ) {

		printf("we got an null start");

	} else {
		double previousNumber = 0;
		struct Calc* numberToAdd;
		struct Calc* operationToAdd;

		for (index = 0; *(stringToCalculate + index) != '\0'; ++index) {
			printf("\n parsing character %c", *(stringToCalculate + index));
			char charParsed = *(stringToCalculate + index);
			//check if char parsed is one of the operations
			if (isCharParsedAOperation(charParsed) == 1) {
				//if is a operation
				//add to the previous number parsed

				numberToAdd = (struct Calc*) malloc(sizeof(struct Calc));
				numberToAdd->valueToParse = previousNumber;
				numberToAdd->isOperation = FALSE;
				prev->next = numberToAdd;

				operationToAdd = (struct Calc*) malloc(sizeof(struct Calc));
				operationToAdd->isOperation = TRUE;
				operationToAdd->valueToParse = charParsed;
				operationToAdd->next = NULL;
				numberToAdd->next = operationToAdd;

				previousNumber = 0;
				prev = operationToAdd;

			} else {
				int intParsed = getIntFromChar(charParsed);

				previousNumber = previousNumber * 10 + intParsed;

				//else keep on adding the current number
				//parsed as number and add to previpus number
			}

		}

	}

}

double calculateValue(double left, double right, double operation) {

	double valueCalculated = 0;

	double plusOperation = '+';
	double minusOperation = '-';
	double multiplicationOperation = '*';
	double divisionOperation = '/';

	if (plusOperation == operation) {
		valueCalculated = left + right;
	} else if (minusOperation == operation) {
		valueCalculated = left - right;
	} else if (multiplicationOperation == operation) {
		valueCalculated = left * right;
	} else if (divisionOperation == operation) {
		valueCalculated = left / right;
	}

	return valueCalculated;

}

struct Calc* calculateValueFromStackCreatedWithOperationDivideOrMultiply(struct Calc* startPtr, char operationToCalculate) {
	struct Calc* temp = startPtr;
	temp = temp->next;
	struct Calc* prev = NULL;
	while (temp != NULL && !(temp->isOperation == TRUE && temp->valueToParse == operationToCalculate)) {
		prev = temp;
		temp = temp->next;
	}
	if (temp != NULL ) {

		double valueCalculated = calculateValue(prev->valueToParse, temp->next->valueToParse, temp->valueToParse);
		prev->valueToParse = valueCalculated;

		prev->next = prev->next->next->next;

		free(temp);
		free(temp->next);

		return calculateValueFromStackCreatedWithOperationDivideOrMultiply(startPtr, operationToCalculate);

	} else {

		return NULL ;
	}

}


struct Calc* calculateValueFromStackCreatedWithOperationAdditionAndSubtraction(struct Calc* startPtr, char additionOperationToCalculate, char subtractionOperationToCalculate) {
	struct Calc* temp = startPtr;
	temp = temp->next;
	struct Calc* prev = NULL;
	while (temp != NULL && !(temp->isOperation == TRUE && (temp->valueToParse == additionOperationToCalculate||temp->valueToParse==subtractionOperationToCalculate))) {
		prev = temp;
		temp = temp->next;
	}
	if (temp != NULL ) {

		double valueCalculated = calculateValue(prev->valueToParse, temp->next->valueToParse, temp->valueToParse);
		prev->valueToParse = valueCalculated;

		prev->next = prev->next->next->next;

		free(temp);
		free(temp->next);

		return calculateValueFromStackCreatedWithOperationAdditionAndSubtraction(startPtr, additionOperationToCalculate,subtractionOperationToCalculate);

	} else {

		return NULL ;
	}

}

void calculateValueFromStackCreated(struct Calc* startPtr) {

	calculateValueFromStackCreatedWithOperationDivideOrMultiply(startPtr, '/');
	printf("\n ==================division operation complete================");
	printCreatedStack(startPtr);

	calculateValueFromStackCreatedWithOperationDivideOrMultiply(startPtr, '*');
	printf("\n ==================multiplication operation complete================");
	printCreatedStack(startPtr);

	calculateValueFromStackCreatedWithOperationAdditionAndSubtraction(startPtr, '+','-');
	printf("\n ==================addition and subtraction operation complete================");
	printCreatedStack(startPtr);

//
//	calculateValueFromStackCreatedWithOperation(startPtr, '-');
//	printf("\n ==================subtract operation complete================");
//	printCreatedStack(startPtr);

	return;

}

void printCreatedStack(struct Calc* Start) {
	struct Calc* temp = Start;
	temp = temp->next;

	printf("\n==================Printing Operation Stack==================\n");

	while (temp != NULL ) {
		printf("\n value:%f isOperation:%d ", temp->valueToParse, temp->isOperation);
		temp = temp->next;
	}

double tempgetDoubleValue(){
	return 5.6;
}

}

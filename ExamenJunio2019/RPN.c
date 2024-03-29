/*
 ============================================================================
 Name        : RPN.c
 Author      : Jacobo Elicha
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include "Stack.h"

int process(char * filename);

int main(void) {
	T_Stack q;
	int ok, result;
	q = create();
	if (isEmpty(q)) puts("Now the queue is empty.");
	else puts("Now the queue contains something.");
	push(&q, 3);
	if (isEmpty(q)) puts("Now the queue is empty.");
	else puts("Now the queue contains something.");
	push(&q, 4);
	push(&q, 5);
	ok = pushOperator(&q, '*');
	if (!ok) puts("* cannot operate");
	ok = pushOperator(&q, '+');
	if (!ok) puts("* cannot operate");
	push(&q, 6);
	ok = pushOperator(&q, '+');
	if (!ok) puts("+ cannot operate");
	ok = pop(&q, &result);
	if (!ok) puts("Cannot pop");
	printf("The result is %d.\n", result);
	if (isEmpty(q)) puts("Now the queue is empty.");
	else puts("Now the queue contains something.");

	result = process("source.calc");
	printf("The result from the file is %d.\n", result);
	return EXIT_SUCCESS;
}

int text2Int(char * text) {
	int value=0, i=0;
	while(isdigit(text[i]))
		value = (value*10)+(text[i++]-'0');
	return value;
}

int isOperator(char * text){
	return !isdigit(text[0]);
}

#define MAX_LENGTH 1024
int process(char * filename) {

	FILE* f = fopen(filename,"r");
	char * cadena = malloc((sizeof(char)*5));
	if(f == NULL){
		perror("No ha sido posible abrir el fichero.");
		exit(-1);
	}

	T_Stack q = create();

	while(!feof(f)){
		fgets(cadena,6,f);
		if(isOperator(cadena)){
			pushOperator(&q,*cadena);
		}else{
			push(&q,text2Int(cadena));
		}
	}

	int result;
	pop(&q,&result);

	fclose(f);

	return result;
}

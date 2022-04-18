/*
 * Stack.c
 *
 *  Created on: 11 jun. 2019
 *      Author: Jacobo
 */
#include <stdio.h>
#include <stdlib.h>
#include "Stack.h"

// typedef struct Node * T_Stack;
// typedef struct Node {
// 	struct Node * next;
// 	int number;
// } T_Node;

// Creates an empty stack.
T_Stack create() {
    // T_Stack stack = malloc(sizeof(T_Node));
    // if (stack == NULL)
    // {
    //     perror("No se ha podido asignar memoria para crear la Stack.");
    //     exit(-1);
    // }
    
    // stack->next = NULL;
    // stack->number = NULL;
    T_Stack stack = NULL;

    return stack; 
}

// Returns true if the stack is empty and false in other case.
int isEmpty(T_Stack q) {
    return q == NULL ? 1 : 0;
}

// Inserts a number into the stack.
void push(T_Stack * pq, int operand) {
    T_Stack ptr = *pq;
    if (ptr == NULL) 
    {
        ptr = malloc(sizeof(T_Node));
        ptr->next = NULL;
        ptr->number = operand;
        *pq = ptr;
    }else{
        while (ptr->next != NULL)
        {
            ptr = ptr->next;
        }
        T_Node* nuevo = malloc(sizeof(T_Node));
        nuevo->next = NULL;
        nuevo->number = operand;
        ptr->next = nuevo;
    }
}

// "Inserts" an operator into the stack and operates.
// Returns true if everything OK or false in other case.
int pushOperator(T_Stack * pq, char operator) {
    T_Stack ptr = *pq;
    int ok = 0;
    if(ptr == NULL || ptr->next == NULL){
        perror("Argumentos insuficientes.");
    }else{
        while(ptr->next->next != NULL){
                ptr = ptr->next;
        }
        switch (operator)
        {
        case '+':
            ptr->number = ptr->number + ptr->next->number;
            free(ptr->next);
            ptr->next = NULL;
            ok = 1;
            break;
        case '-':
            ptr->number = ptr->number - ptr->next->number;
            free(ptr->next);
            ptr->next = NULL;
            ok = 1;
            break;
        case '*':
            ptr->number = ptr->number * ptr->next->number;
            free(ptr->next);
            ptr->next = NULL;
            ok = 1;
            break;
        case '/':
            ptr->number = ptr->number / ptr->next->number;
            free(ptr->next);
            ptr->next = NULL;
            ok = 1;
            break;
        default:
            perror("No se admite la operacion.");
            break;
        }
    }
    return ok;
}

// Puts into data the number on top of the stack, and removes the top.
// Returns true if everything OK or false in other case.
int pop(T_Stack * pq, int * data) {
    T_Stack ptr = *pq;
    int ok = 0;
    if(ptr == NULL){
        perror("No hay datos.");
    }else{
        if(ptr->next == NULL){
            *data = ptr->number;
            (*pq) = NULL; 
            ok = 1;
        }else{
            T_Node* prev = ptr;
            ptr = ptr->next;
            while (ptr->next != NULL)
            {
                prev = ptr;
                ptr = ptr->next;
            }
            *data = ptr->number;
            free(ptr);
            prev->next = NULL;
            ok = 1;
        }
        
    }
    return ok;
}

// Frees the memory of a stack and sets it to empty.
void destroy(T_Stack * pq) {
    T_Node* ptr = *pq;
    if(ptr->next == NULL){
        free(ptr);
        (*pq) = NULL;;
    }else{
        T_Node* prev = ptr;
        ptr = ptr->next;
        while (ptr->next != NULL)
        {
            prev = ptr;
            ptr = ptr->next;
            free(prev);
        }    
    }
    (*pq) = NULL;
}

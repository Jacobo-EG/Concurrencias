/*
 * Planta.c
 *
 *  Created on: 9 abr. 2021
 *      Author: Jacobo
 */


#include "Planta.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

// typedef struct Nodo* ListaHab;
// struct Nodo {
// 	char nombre[20];
// 	unsigned numHab;
// 	unsigned fechaSalida;
// 	ListaHab sig;
// };

void crear(ListaHab *lh){
	*lh = NULL;
}

void nuevoCliente(ListaHab *lh,unsigned nh,char *nombre,unsigned fs){
	ListaHab ptr = *lh;
	ListaHab prev = ptr;

	if(*lh == NULL){
		ListaHab nuevo = malloc(sizeof(struct Nodo));
		if(nuevo == NULL){
			perror("No se ha podido asignar memoria para la creacion de la habitacion.");
			exit(-1);
		}
		nuevo->numHab = nh;
		nuevo->fechaSalida = fs;
		strcpy(nuevo->nombre,nombre);
		nuevo->sig = NULL;

		*lh = nuevo;
	}else{
		
		while(ptr != NULL && ptr->numHab < nh){
			prev = ptr;
			ptr = ptr->sig;
		}
		
		if(ptr == NULL){
			ListaHab nuevo = malloc(sizeof(struct Nodo));
			if(nuevo == NULL){
				perror("No se ha podido asignar memoria para la creacion de la habitacion.");
				exit(-1);
			}
			nuevo->numHab = nh;
			nuevo->fechaSalida = fs;
			strcpy(nuevo->nombre,nombre);
			nuevo->sig = NULL;

			prev->sig = nuevo;
		}else if(ptr->numHab == nh){
			ptr->fechaSalida = fs;
		}else{
			ListaHab nuevo = malloc(sizeof(struct Nodo));
			if(nuevo == NULL){
				perror("No se ha podido asignar memoria para la creacion de la habitacion.");
				exit(-1);
			}
			nuevo->numHab = nh;
			nuevo->fechaSalida = fs;
			strcpy(nuevo->nombre,nombre);
			if(ptr == *lh){
				nuevo->sig = ptr;
				*lh = nuevo;
			}else{
				nuevo->sig = ptr;
				prev->sig = nuevo;	
			}
			
		}
	}
	
}

void imprimir(ListaHab lh){
	if(lh == NULL){
		printf("\n --- Lista vacia --- \n");
	}else{
		while(lh != NULL){
			printf("\t Habitacion %i ocupada por %s con fecha de salida %u ", lh->numHab, lh->nombre, lh->fechaSalida);
			lh = lh->sig;
		}
	}
}

void borrar(ListaHab *lh){
	ListaHab ptr = *lh;
	while(*lh != NULL){
		ptr = *lh;
		*lh = (*lh)->sig;
		free(ptr);
	}
}

void borrarFechaSalida(ListaHab *lh,unsigned fs){
	ListaHab ptr = *lh;
	ListaHab prev = ptr;

	while(ptr != NULL && ptr->fechaSalida == fs){
		prev = ptr->sig;
		free(ptr);
		*lh = prev;
		ptr = prev;
	}

	while(ptr != NULL ){
		if(ptr->fechaSalida == fs){
			prev->sig = ptr->sig;
			free(ptr);
			ptr = prev->sig;
		}else{
			prev = ptr;
			ptr = ptr->sig;
		}
	}
}
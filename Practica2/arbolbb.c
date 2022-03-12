/*
 * arbolbb.c
 *
 *  Created on: 15 mar. 2021
 *      Author: Jacobo Elicha Garrucho
 */
#include <stdio.h>
#include <stdlib.h>
#include "arbolbb.h"

/*struct T_Nodo {
	unsigned dato;
	T_Arbol izq, der;
};*/

// Inicializa la estructura a NULL.
void Crear(T_Arbol* arbol_ptr){
    *arbol_ptr=NULL;
}

// Destruye la estructura utilizada.
void Destruir(T_Arbol *arbol_ptr){
    if(*arbol_ptr!=NULL){
        Destruir(&((*arbol_ptr)->izq));
        Destruir(&((*arbol_ptr)->der));
        free(arbol_ptr);
    }
}

// Inserta num en el arbol
void Insertar(T_Arbol *arbol_ptr,unsigned num)
{
	if(*arbol_ptr==NULL){
        T_Arbol aux = malloc(sizeof(struct T_Nodo));
        aux->dato=num;
        aux->izq=NULL;
        aux->der=NULL;
        *arbol_ptr=aux;
    }else{
        if((*arbol_ptr)->dato!=num){
            if(num<(*arbol_ptr)->dato){
                Insertar(&((*arbol_ptr)->izq),num);
            }else{
                Insertar(&((*arbol_ptr)->der),num);
            }
        }
    }
	
}
// Muestra el contenido del árbol en InOrden
void Mostrar(T_Arbol arbol){
    if(arbol!=NULL){
        if(arbol->izq!=NULL){
            Mostrar(arbol->izq);
        }
        printf("%u\n",arbol->dato);
        if(arbol->der!=NULL){
            Mostrar(arbol->der);
        }
    }
}


// Guarda en disco el contenido del arbol - recorrido InOrden
void Salvar(T_Arbol arbol, FILE *fichero){
    if(arbol!=NULL){
        if(arbol->izq!=NULL){
            Salvar(arbol->izq,fichero);
        }
        fwrite(&(arbol->dato),sizeof(unsigned),1,fichero);
        if(arbol->der!=NULL){
            Salvar(arbol->der,fichero);
        }
    }
	
}

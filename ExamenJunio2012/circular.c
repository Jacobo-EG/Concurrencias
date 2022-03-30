#include <stdlib.h>
#include <stdio.h>
#include "circular.h"

// struct circular
// {
//     int pid;
//     LProc sig;
// };

void Crear(LProc *lista){
    *lista=NULL;
}

void AnyadirProceso(LProc *lista, int idproc){
    LProc aux = malloc(sizeof(struct circular));
    aux->pid=idproc;
    if(*lista==NULL){
        *lista=aux;
    }else{
        LProc ptr = *lista;
        while(ptr->sig!=*lista)
            ptr=ptr->sig;
        ptr->sig=aux;
    }
    aux->sig=*lista;
}

void MostrarLista(LProc lista){
    printf("\n Lista cricular \n");
   if(lista == NULL){
       printf(" --vacia-- \n");
   }else{
       LProc aux = lista;
       do{
           printf("Nodo con id %i \n",aux->pid);
           aux = aux->sig;
       }while(aux!=lista);
   }
}

void EjecutarProceso(LProc *lista){
    if(*lista!=NULL){
        LProc ptr = (*lista)->sig;
        LProc aux = *lista;
        if(aux!=ptr){
            while(aux->sig!=*lista)
                aux=aux->sig;
            aux->sig=ptr;
            free(*lista);
            *lista=ptr;
        }else{
            free(*lista);
            *lista=NULL;
        }
    }
}
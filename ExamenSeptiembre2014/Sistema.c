#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Sistema.h"

// struct Hebra{
//     int prior;
//     char* idhebra;
//     Hebra* sig;
// };

// struct Proceso{
//     int idproc;
//     Hebra* hbr;
//     LSistema sig;
// };

void Crear(LSistema *l){
    *l = NULL;
}

void InsertarProceso ( LSistema *ls, int idproc){

    LSistema ptr = *ls;

    struct Proceso* p = malloc(sizeof(struct Proceso));
    p->idproc = idproc;
    p->hbr = NULL;
    p->sig = NULL;

    if(ptr == NULL){
        *ls = p;  
    }else{
        while(ptr->sig!=NULL)
            ptr=ptr->sig;
        ptr->sig=p;
    }

}

void InsertarHebra (LSistema *ls, int idproc, char *idhebra, int priohebra){
    LSistema ptr = *ls;
    
    //Como suponemos que existe el proceso con idproc, no tenemos que comprobar que este vacia pero lo haremos por seguridad
    
    while(ptr!=NULL && ptr->idproc!=idproc)
        ptr = ptr->sig;

    if(ptr==NULL){
        perror("No existe el proceso \n");
        exit(-1);
    }

    struct Hebra * new = malloc(sizeof(struct Hebra));
    strcpy(new->idhebra, idhebra);
    new->prior = priohebra;
    new->sig = NULL;
    
    struct Hebra * h = ptr->hbr;

    struct Hebra * prev = h;

    while(h != NULL && h->prior > priohebra){
            prev = h;
            h = h->sig;
    }

    if(ptr->hbr == NULL){
       ptr->hbr = new;
    }else{
        if(prev != h){
            prev -> sig = new;
            new->sig = h;
        }else{
            ptr->hbr = new;
            new -> sig = h;
        } 
    }

}

void Mostrar (LSistema ls){
    if(ls == NULL){
        printf("--- No hay procesos ---");
    }
    while(ls!=NULL){
        printf("Identificador proceso: %i, Hebras: \n",ls->idproc);
        if(ls->hbr==NULL)
            printf("        --- No tiene Hebras --- \n");
        struct Hebra * aux = ls->hbr;
        while(aux!=NULL){
            printf("        Identificador Hebra: %s, Prioridad %i \n",aux->idhebra, aux->prior);
            aux = aux->sig;
        }
        ls = ls->sig;
    }
}

void EliminarProc (LSistema *ls, int numproc){
    LSistema ptr = *ls;
    LSistema prev = ptr;

    while(ptr!=NULL && ptr->idproc != numproc){
        prev = ptr;
        ptr = ptr->sig;
    }
    
    if(ptr==NULL){
        printf("--- ADEVERTENCIA --- No se ha encontrado proceso con identificador: %i \n", numproc);
    }else{

        struct Hebra * hr = ptr->hbr;
        if(ptr->hbr!=NULL){

            ptr->hbr = NULL;
            struct Hebra * aux = hr;

            while(hr!=NULL){
                aux = aux->sig;
                free(hr);
                hr = aux;
            }
        }

        if(prev!=ptr){
            prev->sig = ptr->sig;
            free(ptr);
        }else{
            *ls = ptr->sig;
            free(ptr);
        }
    }
}

void Destruir (LSistema *ls){
    LSistema ptr = *ls;
    LSistema aux = ptr->sig;
    while(ptr!=NULL){
        EliminarProc(ls,ptr->idproc);
        ptr = aux;
        if(aux!=NULL){
            aux = aux->sig;
        }
    }
}
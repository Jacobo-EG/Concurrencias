#include <stdio.h>
#include <stdlib.h>
#include "Lista.h"

void crearLista(TLista *lista){
    *lista = NULL;
}

void insertarPunto(TLista *lista, struct Punto punto, int * ok){
    if(*lista == NULL){
        struct Nodo* aux = malloc(sizeof(struct Nodo));
        aux->punto.x = punto.x;
        aux->punto.y = punto.y;
        aux->sig = NULL;
        *lista = aux;
        *ok = 1;
    }else{
        TLista ptr = *lista;
        if(ptr->punto.x > punto.x){
            struct Nodo* aux = malloc(sizeof(struct Nodo));
            aux->punto.x = punto.x;
            aux->punto.y = punto.y;
            aux->sig = ptr;
            *lista = aux;
            *ok = 1;
        }else{
            TLista prev;
            while(ptr->sig != NULL && ptr->punto.x < punto.x){
                prev = ptr;
                ptr = ptr->sig;
            }
            
            if(ptr->sig == NULL){
                struct Nodo* aux = malloc(sizeof(struct Nodo));
                aux->punto.x = punto.x;
                aux->punto.y = punto.y;
                aux->sig = NULL;
                ptr->sig = aux;
                *ok = 1;
            }else if(ptr->punto.x == punto.x){
                *ok = 0;
            }else{
                struct Nodo* aux = malloc(sizeof(struct Nodo));
                aux->punto.x = punto.x;
                aux->punto.y = punto.y;
                aux->sig = ptr;
                prev->sig = aux;
                *ok = 1;
            }
        }
        
    }
}

void eliminarPunto(TLista *lista,float x,int* ok){
    *ok = 0;
    TLista ptr = *lista;
    TLista prev = ptr;

    if(ptr != NULL && ptr->punto.x == x){
        *lista = ptr->sig;
        free(ptr);
        *ok = 1;
    }else{
        while(ptr != NULL && ptr->punto.x != x){
            prev = ptr;
            ptr = ptr->sig;
        }
        if(ptr != NULL){
            prev->sig = ptr->sig;
            free(ptr);
        }
    }
}

void mostrarLista(TLista lista){
    printf("Lista: \n");
    if(lista == NULL){
        printf("\n --- Lista vacia --- \n");
    }else{
        while(lista != NULL){
            printf("\n x: %f, y: %f \n",lista->punto.x,lista->punto.y);
            lista = lista->sig;
        }
    }
}

void destruir(TLista *lista){
    TLista ptr = *lista;
    TLista aux = *lista;
    while(ptr != NULL){
        aux = ptr;
        ptr = ptr->sig;
        free(aux);
    }
    *lista = NULL;
}

void leePuntos(TLista *lista,char * nFichero){
    struct Punto punto;
    int ok;
    FILE* f = fopen(nFichero,"rb");

    if(f == NULL){
        perror("No se ha podido abrir el fichero.");
        exit(-1);
    }

    while(!feof(f)){
        fread(&punto.x,sizeof(float),1,f);
        fread(&punto.y,sizeof(float),1,f);
        insertarPunto(lista,punto,&ok);
    }
}
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Mprocesos.h"

void Crear(LProc * lroundrobin){
    *lroundrobin = NULL;
}

void AnadirProceso(LProc* lroundrobin, int idproc){

    LProc ptr = *lroundrobin;

    LProc nuevo = malloc(sizeof(struct Proceso));
    nuevo->idproc = idproc;

    if(ptr==NULL){
        nuevo->sig = nuevo;
        *lroundrobin = nuevo;
    }else{
        nuevo->sig = ptr->sig;
        ptr->sig = nuevo;
        *lroundrobin = nuevo;
    }
}

void EjecutarProcesos(LProc lroundrobin){
    
    if(lroundrobin==NULL){
        printf("--- Lista vacia --- \n");
    }else{
        int inicial = lroundrobin->idproc;
        printf("Proceso %i \n",lroundrobin->idproc);
        lroundrobin = lroundrobin->sig;
        while(lroundrobin->idproc != inicial){
            printf("Proceso %i \n",lroundrobin->idproc);
            lroundrobin = lroundrobin->sig;
        }
    }
}


void EliminarProceso(int id, LProc *lista){
    //Como suponemos que se encuentra al menos el proceso con idproc=id entonces no tenemos que comprobar que este vacia

    LProc ptr = (*lista)->sig;
    LProc prev = *lista;
    while (ptr->idproc != id)
    {
        ptr = ptr->sig;
        prev = prev->sig;
    }

    if (ptr->idproc == prev->idproc)
    {
        free(ptr);
        *lista = NULL;
    }else{
        prev->sig = ptr->sig;
        free(ptr);
        *lista = prev;
    }
    
    
}

void EscribirFichero (char * nomf, LProc *lista){
    FILE* f = fopen(nomf,"wb"); 

    if(f == NULL){
        perror("Error al abrir el fichero");
        exit(-1);
    }

    LProc ptr = *lista;
    unsigned num;
    int inicial;
    
    if(ptr == NULL){
        num = 0;
        fwrite(&num,sizeof(unsigned),1,f);
    }else{
        num = 1;
        inicial = ptr->idproc;
        ptr = ptr->sig;

        while(ptr->idproc != inicial){
            num ++;
            ptr = ptr->sig;
        }

        fwrite(&num,sizeof(unsigned),1,f);
        int cont=0;

        while(cont<num){
            fwrite(&((*lista)->idproc),sizeof(int),1,f);
            ptr = *lista;
            *lista = (*lista)->sig;
            free(ptr);
            cont++;
        }

        *lista = NULL;
    }

    fclose(f);

}
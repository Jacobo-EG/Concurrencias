#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Componentes.h"

// typedef struct Componente {
// 	long codigoComponente;
// 	char textoFabricante[MAX_CADENA];
// 	struct elemLista * sig;
// }

Lista Lista_Crear(){
    Lista * l =  malloc(sizeof(Componente));

    if(l == NULL){
        perror("No se ha podido crear la lista. ");
    }else{
        *l = NULL;
    }
    return *l;
}

 void Adquirir_Componente(long *codigo,char *texto){
     printf("Introduce el codigo del articulo: ");
     scanf("%ld",codigo);
     printf("Introduce el texto del articulo: ");
     scanf(" %[^\n]",texto);
 }

void Lista_Agregar(Lista *lista, long codigo, char* texto){
    Lista ptr = *lista;
    Componente * new = malloc(sizeof(Componente));

    new -> codigoComponente = codigo;
    strcpy(new -> textoFabricante, texto);
    new -> sig = NULL;

    if(ptr!=NULL){
        while(ptr->sig != NULL) ptr = ptr->sig;
        ptr -> sig = new;
    }else{
        *lista = new;
    }

}

void Lista_Imprimir( Lista lista){
    if(lista==NULL){
        printf("-- Lista vacia -- \n");
    }
    while(lista!=NULL){
        printf("Codigo: %ld, texto: %s \n",lista->codigoComponente,lista->textoFabricante);
        lista = lista->sig;
    }
}

int Lista_Vacia(Lista lista){
   return lista == NULL ? 1 : 0;
}

int Num_Elementos(Lista lista){
    int cont = 0;

    while(lista!=NULL){
        lista = lista->sig;
        cont++;
    }
    
    return cont;
}

void Lista_Vaciar(Lista *lista){
    while(Lista_Vacia(*lista) == 0){
        Lista_Extraer(lista);
    }
}

void Lista_Extraer(Lista *lista){
    Lista ptr = *lista;
    Lista prev = *lista;
    if(ptr!=NULL){
        if(ptr->sig!=NULL){
            while(ptr->sig!=NULL){
                prev = ptr;
                ptr=ptr->sig;
            }
            free(ptr);
            prev->sig = NULL;
        }else{
            free(ptr);
            *lista = NULL;
        }
    }
}

void Lista_Salvar(Lista  lista){
    FILE * f = fopen("examen.dat","wb");

    if(f == NULL){
        perror("Error al abrir el archivo");
        exit(-1);
    }
    while(lista!=NULL){
        fwrite(&lista->codigoComponente, sizeof(typeof(lista->codigoComponente)), 1, f);
        fwrite(&lista->textoFabricante, sizeof(typeof(*lista->textoFabricante)), strlen(lista->textoFabricante), f);
        lista=lista->sig;
    }

}
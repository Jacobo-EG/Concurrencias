#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include "Tree.h"

//Jacobo Elicha Garrucho

/*typedef struct Node *Tree;
typedef struct Node
{
	char *name;
	double lat, lon;
	Tree left, right;
} Node;*/

void inicializarArbol(Tree *ptrTree){
    *ptrTree = NULL;
}

void insertarComisaria(Tree *ptrTree, char *name, double lat, double lon){

    if(*ptrTree == NULL){
        Tree nuevo = malloc(sizeof(struct Node));
        if(nuevo == NULL){
            perror("Error pidiendo memoria para el nuevo nodo.");
            exit(-1);
        }

        nuevo->name = malloc(sizeof(char)*(strlen(name)+1)); //sizeof(char)*(strlen(name)+1) = strlen(name) + 1
        if(nuevo->name == NULL){
            perror("Error pidiendo memoria para nombre de nuevo nodo.");
            exit(-1);
        }

        nuevo->lat = lat;
        nuevo->lon = lon;
        strcpy(nuevo->name,name);
        nuevo->left = NULL;
        nuevo->right = NULL;
        *ptrTree = nuevo;
    }else{
        Tree ptr = *ptrTree;
        if(strcmp((*ptrTree)->name,name)>0){
            insertarComisaria(&(*ptrTree)->left,name,lat,lon);
        }else if(strcmp((*ptrTree)->name,name)<0){
            insertarComisaria(&(*ptrTree)->right,name,lat,lon);
        }else{
            perror("\n Esta comisaria ya existe \n");
        }
    }
}

void mostrarArbol(Tree t){
    if(t!=NULL){
        if(t->left != NULL){
            mostrarArbol(t->left);
        }

        printf("\n Comisaria con nombre: %s, lat: %d, lon: %d",t->name,t->lat,t->lon);
    
        if(t->right != NULL){
            mostrarArbol(t->right);
        }
    }else{
        printf("\n No hay comisarias \n");
    }
}

void destruirArbol(Tree *ptrTree){
    Tree ptr = *ptrTree;
    if(ptr != NULL){
        destruirArbol(&(ptr->left));
        destruirArbol(&(ptr->right));
        free(ptr);
    }
    *ptrTree = NULL;
}

char* localizarComisariaCercanaAUX(Tree t,double lat,double lon,int* menor){
    int* menorI, * menorD;
    char * res;
    if(t->left!=NULL){
        localizarComisariaCercanaAUX(t->left,lat,lon,menorI);
    }
    *menor = fabs(lat - t->lat) + fabs(lon - t->lon);
    if(t->right != NULL){
        localizarComisariaCercanaAUX(t->right,lat,lon,menorD);
    }
    
    if(*menor < *menorI && *menor < *menorD){
        res = malloc(sizeof(char)*(strlen(t->name)+1));
        if(res == NULL){
            perror("Error pidiendo memoria");
            exit(-1);
        }
        strcpy(res,t->name);
    }else if(*menor > *menorI && *menorI < *menorD){
        res = malloc(sizeof(char)*(strlen(localizarComisariaCercanaAUX(t->left,lat,lon,menorI))+1));
        if(res == NULL){
            perror("Error pidiendo memoria");
            exit(-1);
        }
        strcpy(res,localizarComisariaCercanaAUX(t->left,lat,lon,menorI));
    }else{
        res = malloc(sizeof(char)*(strlen(localizarComisariaCercanaAUX(t->right,lat,lon,menorD))+1));
        if(res == NULL){
            perror("Error pidiendo memoria");
            exit(-1);
        }
        strcpy(res,localizarComisariaCercanaAUX(t->right,lat,lon,menorD));
    }
    return res;
}

char *localizarComisariaCercana(Tree t, double lat, double lon){
    char * res;
    int* menor ;
    if(t == NULL){
        res = NULL;
    }else{
        *menor = fabs(lat - t->lat) + fabs(lon - t->lon);
        res = malloc(sizeof(char)*(strlen(localizarComisariaCercanaAUX(t,lat,lon,menor))+1));
        
    }
    return res;
}

void cargarComisarias(char *filename, Tree *ptrTree){
    FILE * f = fopen(filename,"r");
    if(f == NULL){
        perror("Error al abrir el fichero");
        exit(-1);
    }

    double lat, lon;
    char nombre[255];

    while(!feof(f)){
        fscanf(f,"^[;]",nombre,lat,lon);
        insertarComisaria(ptrTree,nombre,lat,lon);
    }

    fclose(f);
}


void guardarBinarioAUX(FILE* f, Tree tree){
    if(tree != NULL){
        if(tree->left != NULL){
            guardarBinarioAUX(f,tree->left);
        }
        unsigned* tam;
        *tam = strlen(tree->name);
        fwrite(tam,sizeof(unsigned),1,f);
        fwrite(tree->name,sizeof(char),strlen(tree->name),f);
        fwrite(&(tree->lat),sizeof(double),1,f);
        fwrite(&(tree->lat),sizeof(double),1,f);
    
        if(tree->right != NULL){
            guardarBinarioAUX(f,tree->right);
        }
    }
}

void guardarBinario(char *filename, Tree tree){
    FILE* f = fopen(filename,"wb");
    if(f == NULL){
        perror("Error al abrir el fichero");
        exit(-1);
    }
    guardarBinarioAUX(f,tree);    
    fclose(f);
}
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
struct str_Libro
{
    char titulo[200];
    char autor[200];
    int paginas;
    float precio;
};

enum Meses { ENE, FEB, MAR, ABR, MAY, JUN, JUL, AGO, SEP, OCT, NOV, DIC };

struct str_Revisa_Mensual
{
    char titulo[200];
    enum Meses mes;
    float precio;
};

union u_Item{
    struct str_Libro libro;
    struct str_Revisa_Mensual revista;
};

typedef struct str_union_item * ptr_str_union_item;

struct str_union_item{
    union u_Item item;
    char tipo;
    ptr_str_union_item siguiente;  
};

/*
1- Se proporciona el código para rellenar tres item de esa lista enlazada. ¿Cómo se puede eliminar el primer elemento de la lista? Recuerda liberar la memoria.

2.-Comenta el código anterior y prueba ahora, haciendo uso de un bucle, a eliminar la lista entera. Recuerda liberar la memoria.

*/

int main(int argc, char const *argv[])
{
    ptr_str_union_item lista = malloc(sizeof(struct str_union_item));
    (*lista).tipo='l';
    strcpy(lista->item.libro.titulo,"HOla mundo book");
    strcpy(lista->item.libro.autor,"Joaquin Ballesteros");
    lista->item.libro.paginas=100;
    lista->item.libro.precio=25.5;

    lista->siguiente= malloc(sizeof(struct str_union_item));
    lista->siguiente->tipo='l';
    strcpy(lista->siguiente->item.libro.titulo,"Hi there budy");
    strcpy(lista->siguiente->item.libro.autor,"Carlos Bustamante");
    lista->siguiente->item.libro.paginas=50;
    lista->siguiente->item.libro.precio=15.5;

    lista->siguiente->siguiente= malloc(sizeof(struct str_union_item));
    lista->siguiente->siguiente->tipo='r';
    strcpy(lista->siguiente->siguiente->item.revista.titulo,"Marca gol");
    lista->siguiente->siguiente->item.revista.mes=1;
    lista->siguiente->siguiente->item.revista.precio=3.5;
    lista->siguiente->siguiente->siguiente=NULL;


    ptr_str_union_item ptr = lista; //Lo creamos por que si usasemos el propio puntero lista al iterar perderiamos los elementos 

    while(ptr!=NULL && (ptr->tipo!='k')){
        printf("Titulo %s, precio %.2f\n",(ptr->tipo=='r'?ptr->item.revista.titulo:ptr->item.libro.titulo),(ptr->tipo=='r'?ptr->item.revista.precio:ptr->item.libro.precio));
        ptr = ptr->siguiente;

    }

    // ptr_str_union_item aux = lista->siguiente;
    // free(lista);
    // lista = aux;     //Es equivalente a lo siguiente:
    ptr_str_union_item aux = lista;
    lista=lista->siguiente;
    free(aux);

    ptr = lista; 

    while(ptr!=NULL && (ptr->tipo!='k')){
        printf("Titulo %s, precio %.2f\n",(ptr->tipo=='r'?ptr->item.revista.titulo:ptr->item.libro.titulo),(ptr->tipo=='r'?ptr->item.revista.precio:ptr->item.libro.precio));
        ptr = ptr->siguiente;

    }

    //Para borrar el ultimo de la lista
    ptr=lista;
    if(lista!=NULL){
        while(ptr->siguiente!=NULL){
            ptr=ptr->siguiente;
        }
        free(ptr);
        lista = (ptr==lista?NULL:lista);    //Si solo tiene un elemento debemos evitar que lista se quede apuntando a 'basura'
    }
    //Para borrar toda la lista
     ptr=lista;
     while(lista!=NULL){
         lista=lista->siguiente;
         free(ptr);
         ptr=lista;
     }

    return 0;
}
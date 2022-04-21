#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cifrado.h"

// typedef struct TBox *TCifrado;
// struct TBox {
//   unsigned char esSBox;      //verdadero si es una SumaBox, falso si es una XORBox
//   unsigned char bitACambiar; //valor para indicar si en una XORBox se
//                              //cambia el valor del primer o del ultimo bit,
//                              //segun las constantes indicadas arriba
//   unsigned char valorASumar; //en una SumaBox: valor a sumar
//   struct TBox *sig;
// };

void crearEsquemaDeCifrado (TCifrado *cf){
    *cf = NULL;
}

void insertarBox (TCifrado * cf, struct TBox box, unsigned char *ok){
    if(*cf == NULL ){
        if(box.esSBox){
            struct TBox * aux = malloc(sizeof(struct TBox));
            aux->valorASumar = box.valorASumar;
            aux->esSBox = box.esSBox;
            aux->sig = NULL;
            *cf = aux;
            *ok = 1;
        }else{
            *ok = 0;
        }
    }else{
        TCifrado ptr = *cf;
        while(ptr->sig != NULL){         
            ptr = ptr->sig;
        }
        if(ptr->esSBox != box.esSBox){
            struct TBox * aux = malloc(sizeof(struct TBox));
            if(box.esSBox){
                aux->valorASumar = box.valorASumar;
                aux->esSBox = box.esSBox;
                aux->sig = NULL;
                ptr->sig = aux;
            }else{
                aux->bitACambiar = box.bitACambiar;
                aux->esSBox = box.esSBox;
                aux->sig = NULL;
                ptr->sig = aux;
            }
            *ok = 1;
        }else{
            *ok = 0;
        }
    }
}

unsigned char aplicarBox (struct TBox box, unsigned char valor){
    unsigned char resultado;
    if(box.esSBox){
        resultado = box.valorASumar + valor;
    }else{
        if(box.bitACambiar == CAMBIA_BIT_POS_0){
            resultado = valor^1;
        }else{
            resultado = valor^128;
        }
    }
    return resultado;
}

unsigned char aplicarEsquemaDeCifrado(TCifrado cf, unsigned char valor){
    unsigned char resultado = valor;
    while(cf != NULL){
       resultado = aplicarBox(*cf,resultado);
       cf = cf->sig;
    }
    return resultado;
}

void escribirAFichero(char *nm, TCifrado cf){
    FILE* f = fopen(nm, "wb");

    if(f == NULL){
        perror("No ha sido posible abrir el fichero.");
        exit(-1);
    }

    while(cf != NULL){
        fwrite(&(cf->esSBox),sizeof(unsigned char),1,f);
        if(cf->esSBox){
            fwrite(&(cf->valorASumar),sizeof(unsigned char),1,f);
        }else{
            fwrite(&(cf->bitACambiar),sizeof(unsigned char),1,f);
        }
        cf = cf->sig;
    }

    fclose(f);
}

void destruirEsquemaDeCifrado (TCifrado *cf){
    TCifrado ptr;
    while(*cf != NULL){
        ptr = *cf;
        *cf = (*cf)->sig;
        free(ptr);
    }
}
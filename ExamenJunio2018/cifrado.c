#include <stdio.h>
#include <stdlib.h>
#include "cifrado.h"

void crearEsquemaDeCifrado (TCifrado *cf){
    *cf = NULL;
}

void insertarBox (TCifrado * cf, struct TBox box, unsigned char *ok){
    if(*cf == NULL ){
        if(box.esSBox){
            box.sig = NULL;
            *cf = &box;
            *ok = 1;
        }else{
            *ok = 0;
        }
    }else{
        TCifrado ptr = *cf;
        while(ptr->sig != NULL){        //Error aqui ----------
            ptr = ptr->sig;
        }
        if(ptr->esSBox != box.esSBox){
            box.sig = NULL;
            ptr->sig = &box;
            *ok = 1;
        }
    }
}

unsigned char aplicarBox (struct TBox box, unsigned char valor){
    unsigned char resultado;
    if(box.esSBox){
        resultado = box.valorASumar + valor;
    }else{
        if(box.bitACambiar == 0){
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
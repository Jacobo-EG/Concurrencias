#include <stdlib.h>
#include <stdio.h>
#include "gestion_tremor.h"

/*
Practica 2, Jacobo Elicha Garrucho, 09-03-2022
*/

// struct T_Nodo {
// 	time_t fecha;
// 	unsigned duracion;
// 	T_Lista sig;
// };

/* Muestra los episodios de tremor que ha tenido el usuario por orden cronológico, primero los más nuevos, se le pasa la cabeza de la lista  */
	void mostrar_nuevos2antiguos (T_Lista lista){
        if (lista!=NULL){
            mostrar_nuevos2antiguos(lista->sig);
            printf("Evento en %s con duracion %i\n",ctime(&(lista->fecha)),lista->duracion);
        }
    }



/* 
Registra un episodio de tremor, con su fecha y duración, OK es igual a 1 si se puede, 0 si no es posible pedir memoria
 */
	void registrar(T_Lista * ptr_lista_head, const time_t * fecha, unsigned duracion,unsigned* ok){
        T_Lista ptr = malloc(sizeof(struct T_Nodo));
        if(ptr!=NULL){
            ptr->fecha=(*fecha);
            ptr->duracion=duracion;
            ptr->sig=NULL;
            if((*ptr_lista_head)==NULL){
              (*ptr_lista_head)=ptr;
            }else{
                struct T_Nodo * aux = (*ptr_lista_head);
                while(aux->sig!=NULL){
                    aux=aux->sig;
                }
                aux->sig=ptr;
            }
            *ok=1;
        }else{
            *ok=0;
        }
    }

/* 
Libera todos los episodios que son anteriores a la fecha dada. Devuelve el número que se ha eliminado.
 */
	int liberar(T_Lista * ptr_lista_head, const time_t *  fecha){
        int num_lib=0;
        T_Lista aux;
        while((*ptr_lista_head)!=NULL && ((*ptr_lista_head)->fecha)<(*fecha)){
            aux=(*ptr_lista_head);
            (*ptr_lista_head)=((*ptr_lista_head)->sig);
            free(aux);
            num_lib++;
        }
        return num_lib;
    }

/* Destruye la estructura utilizada (libera todos los nodos de la lista. El parámetro manejador debe terminar apuntando a NULL */

	void destruir(T_Lista* ptr_lista_head){
        T_Lista ptr;
        while(*ptr_lista_head!=NULL){
            ptr = *ptr_lista_head;
            *ptr_lista_head=(*ptr_lista_head)->sig;
            free(ptr);
        }
    }
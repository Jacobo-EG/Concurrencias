#ifdef _WIN32
#include <Windows.h>
#else
#include <unistd.h>
#endif

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "gestion_tremor.h"

int main(void) {

	int ok;
	T_Lista lista_tremor_head = malloc(sizeof(struct T_Nodo));
	time_t now_time;
	time_t pass_time;
	
	time( &now_time );
	lista_tremor_head->fecha=now_time;
    lista_tremor_head->duracion=60;
    lista_tremor_head->sig=NULL;


	//Nos paramos un segundo, para simular un retraso en el registro de nuevos episodios
	#ifdef _WIN32
	Sleep(1000);
	#else
	sleep(1);
	#endif

    time( &now_time );
    T_Lista nuevo = malloc(sizeof(struct T_Nodo));
	nuevo->fecha=now_time;
    nuevo->duracion=10;
    nuevo->sig=NULL;

    lista_tremor_head->sig=nuevo;

    destruir(&lista_tremor_head);


    return EXIT_SUCCESS;
}
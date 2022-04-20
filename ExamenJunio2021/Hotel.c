/*
 * Hotel.c
 *
 *  Created on: 9 abr. 2021
 *      Author: Jacobo Elicha
 */

#include "Hotel.h"
#include "Planta.h"
#include "Planta.c"

void crearHotel(ListaHab* h,unsigned NPlantas){
    for(int i = 0;i < NPlantas; i++){
        crear(&(h[i]));
    }
}

void nuevoClienteHotel(ListaHab *h,unsigned NPlantas,unsigned nh,char *nombre,unsigned fs){
    if(nh/10 > NPlantas || fs < 1 || fs > 31){
        perror("No se puede añadir este cliente.");
    }else{
        int planta = nh/10;
        int hab = nh%10;

        nuevoCliente(&(h[planta]),hab,nombre,fs);
    }
}

void imprimirHotel(ListaHab *h,unsigned NPlantas){
    for(int i = 0; i < NPlantas; i++){
        printf("\n Planta %i: ",i);
        imprimir(h[i]);
    }
}

void borrarHotel(ListaHab *h,unsigned NPlantas){
    for (int i = 0; i < NPlantas; i++)
    {
        borrar(&(h[i]));
    } 
}

void borrarFechaSalidaHotel(ListaHab *h,unsigned NPlantas,unsigned fs){
    for (int i = 0; i < NPlantas; i++)
    {
        borrarFechaSalida(&(h[i]),fs);
    }
}
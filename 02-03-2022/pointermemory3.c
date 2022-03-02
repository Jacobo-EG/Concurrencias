/*
1.- Define el siguiente procedimiento, el cual dadas dos variables te intercambia sus valores:
void swap(int x, int y){
  int temp;
  temp = x;
  x = y;
  y = temp;
}

En el main, crea dos enteros y llama a swap. 
 a) ¿Funciona? No, siguen igual.
 b) Arreglalo con el uso de punteros.
*/


/*
2.- Define el siguiente procedimiento, el cual dadas dos variables te intercambia sus valores:
void swap(int *x, int *y){
  int *temp;
  temp = x;
  x = y;
  y = temp;
}

En el main, crea dos enteros y llama a swap. 
 a) ¿¡Por qué no funciona!? El puntero a un entero es local, luego al salir del swap se eliminan las variables
*/

#include <stdio.h>

/* 1---
void swap(int x, int y){
    int temp;
    temp=x;
    x=y;
    y=temp;
}*/

void swap(int * x, int * y){
    int temp;
    temp = *x;
    *x = *y;
    *y = temp;
}

 int main(int argc, char const *argv[])
 {
    int x,y;
    x=10;
    y=5;
    swap(&x,&y);
    printf("%i %i",x,y);
    return 0;
 }
/*Un puntero en C no es más que una variable que tiene el valor en memoria en la que se almacena otra variable. Se definen variables de tipo puntero con el operador unario indirección *

Por ejemplo:
int a; /// en dónde uses a, estás usando un valor númerico entero.

int * b; 
int* b;
int *b;
/// b es de tipo puntero a un entero. Dónde uses b estás usando una dirección de memoria. Si vas a esa dirección de memoria, puedes encontrar un entero o no, dependiendo de lo que contenga esa memoria.

El operador unario dirección & se usa para conocer dónde está en memoria almacenada una variable.

1.- En el siguiente código, 

int xx = 10;
int *ptr;
ptr = &xx;

¿Qué valor tiene ptr? Usa el debug.
¿Qué valor tiene ptr? Usa el printf con el specifier p

2.- En el siguiente ejemplo vamos declarar una cadena y dos punteros a esa cadena, uno haciendo uso del operador indirección y otro asignando directamente.

    char cadena[100]="Hello!";
    char *c1;
    char *c2;
    c1=&cadena[0];
    c2=cadena;
    //printf("%p %p",&cadena[0],cadena);

¿Por qué ambos dan lo mismo? Por que los arrays son punteros (tipo char en este caso) a su primera direccion

3.- Un tipo puntero (char *, int *, double *) permite ciertos operadores sobre el, por ejemplo, la suma.

double numeros_array[]={2.3,3.3,4.4,5.5};
double d;
d=numeros_array;
printf("%lf %lf",d,*(d+2));

¿Por qué funciona el asignar numeros_array a un puntero a double?

4.- ¿Que valor tiene z tras ejecutar el siguiente código?  ¡Usa el debug!
int x =10;
int y = 20;
int z[]={1,2,3};
int *ptri;

ptri = &x;
y = *ptri;

*ptri = 0;
ptri=&z[0]; // ptri=z;
*/


#include <stdio.h>
 int main(int argc, char const *argv[])
 {
     //1
    /*int xx = 10;
    int *ptr;
    ptr = &xx;*/

    //2
    /*char cadena[100]="Hello!";
    char *c1;
    char *c2;
    c1=&cadena[0];
    c2=cadena;
    printf("%p %p",&cadena[0],cadena);*/

    //3
    /*double numeros_array[]={2.3,3.3,4.4,5.5};
    double* d;
    d=numeros_array;
    printf("%lf %lf",*d,*(d+2));*/

    //4
    int x =10;
    int y = 20;
    int z[]={1,2,3};
    int *ptri;

    ptri = &x;
    y = *ptri;

    *ptri = 0;
    ptri=&z[0]; // ptri=z;

    return 0;
 }
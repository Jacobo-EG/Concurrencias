/*
Vamos a hacer un multiplicador de números float.
El usuario va a introducir dos números y se le va a dar el resultado de la multiplicación.

Funciones útiles:
int scanf ( const char * format, ... );
https://www.cplusplus.com/reference/cstdio/scanf/?kw=scanf

*/
#include <stdio.h>

int main(int argc, char const *argv[])
{
    float a,b;
    printf("Introduce dos float separados por un espacio\n");
    if (scanf("%f %f",&a,&b)==2)
    {
        printf("Resultado: %f",a*b);
    }
    else
    {
        perror("Se esperaban dos float separados por un espacio y no se han encontrado\n");
    }
    
    return 0;
}
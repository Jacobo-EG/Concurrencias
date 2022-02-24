/*
Vamos a generar cadenas a partir de otras variables. Esto en C no es trivial. Una cadena es un array de caracteres que terminan en en el carácter ASCII 0 ('\0').
1.- Genera la cadena, tienes varias formas:
    char cadena[11]="Hola mundo";
    char cadena[11] = {'h','o','l','a',' ','m','u','n','d','o','\0'};

2.- Genera una nueva cadena de tamaño 15 y copia su contenido con strcpy(destino, origen).
http://www.cplusplus.com/reference/cstring/strcpy/?kw=strcpy

3. ¿Se puede saber el tamaño del string en la nueva cadena? usa strlen
http://www.cplusplus.com/reference/cstring/strlen/?kw=strlen

4. La concatenación de cadenas se realiza con strcat, crea una cadena de tamaño 30 y pega dos veces el contenido de la cadena[11] definida en el punto 
http://www.cplusplus.com/reference/cstring/strcat/?kw=strcat

5.- Ojo, la comparación no se puede realizar con ==, se usa strcmp(cadena,"a comparar")==0. Prueba a leer con scanf una cadena y a compararla con algo que esperas, con == y con strcmp.
http://www.cplusplus.com/reference/cstring/strcmp/?kw=strcmp

6. La generación de cadenas combinando variables de cualquier tipo se puede realizar con sprintf. Es igual que printf, pero primero va la cadena en la que se almacena. Prueba a concatenar la cadena generada anteriormente con un entero con valor 5 y witdh 10 alineado a la izquierda.
http://www.cplusplus.com/reference/cstdio/sprintf/?kw=sprintf

*/
#include<stdio.h>
#include<string.h>
int main(int argc, char const *argv[])
{
    char cadena[11]="Hola mundo";

    if (strcmp(cadena,"Hola mundo")==0)
    {
        printf("La cadena es igual\n");
    }else{
        printf("La cadena NO es igual\n");
    }
    
    return 0;
}
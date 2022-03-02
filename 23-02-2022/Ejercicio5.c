/*
Enunciado: Vamos a hacer el esqueleto de un menú interactivo.
El usuario introduce un carácter que representa una de las siguientes opciones de menú: 

i para iniciar
1 para opción de llamada1
2 para opción de llamada1
f para fin

Tras leer, imprimimos un mensaje, elige el mensaje que quieras. Cuando se lee un f de fin, se termina el programa.
 Si se lee algo que no corresponde con una opción de menú, se avisa al usuario que no se conoce esa opción, 
 y se le muestra lo que ha introducido.

//Usa para hacerlo:
int getchar(void)
int putchar (int character);
switch
*/
#include<stdio.h>
#include<string.h>
int main(int argc, char const *argv[])
{
    char caracter, inicio='i', fin='f', llamada1='1',llamada2='2';

    printf("Teclee i para iniciar: ");
    caracter=getchar();
    if(caracter == inicio){
        printf("Seleccione 1 para llamada 1, 2 para llamada2, 2 para la llamada 2, o f para finalizar");
        caracter = getchar();
        while(caracter!=fin){
            if(caracter == llamada1){
                printf("Llamada1");
            }else if(caracter == llamada2){
                printf("Llamada2");
            }else{
                printf("No se conoce el caracter que ha introducido: ");
                putchar(caracter);
            }
            caracter=getchar();
        }

    }else{
        printf("No se ha iniciado el menú");
    }
    return 0;
}
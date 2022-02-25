/*Define la siguiente extructura:
struct str_Libro
{
    char titulo[200];
    char autor[200];
    int paginas;
    float precio;
};
1.- Define:
 a) una variable de esta estructura llamada libro.
 b) un puntero a la estructura que apunte a esta variable llamada ptr_libro.
 ¿Puedes comprobar con el debugger que funciona esta asignación?

2.- ¿Puedes mostrar el autor y el precio con printf usando sólo el puntero?*/

#include <stdio.h>

struct str_Libro
{
    char titulo[200];
    char autor[200];
    int paginas;
    float precio;
};

 int main(int argc, char const *argv[])
 {
    struct str_Libro libro;
    struct str_Libro * ptr_libro;

    strcpy(libro.autor,"ErJaki");
    strcpy(libro.titulo,"Tears of my students");
    libro.paginas=12;
    libro.precio=10.5;

    ptr_libro = &libro;
    printf("Nombre %s, precio %f",ptr_libro->autor,(*ptr_libro).precio); //Es lo mismo la forma con '->' que lo segundo 

    return 0;
 }


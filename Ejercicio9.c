/*Vamos a crear una tienda con 100 artículos máximo, vamos a tener libros y revistas.

struct str_Libro
{
    char titulo[200];
    char autor[200];
    int paginas;
    float precio;
};

enum Meses { ENE, FEB, MAR, ABR, MAY, JUN, JUL, AGO, SEP, OCT, NOV, DIC };

struct str_Revisa_Mensual
{
    char titulo[200];
    enum Meses mes;
    float precio;
};
1.- En cada elemento de el array vamos a poder almacenar un libro O una revista, pero no los dos. Genera una nueva estructura str_item que contenga un Libro, una Revisa_Mensual y un char que almacene 'r' si es revisa, 'l' si es libro y 'v' si no hay nada.
2.-Genera un menu interactivo para introducir y ver mostrar todos los ítem que hay en la tienda.
3.-usa sizeof(struct str_item) ¿Cuántos bytes necesitamos por ítem?*/

#include<stdio.h>
#include<string.h>
struct str_Libro
{
    char titulo[200];
    char autor[200];
    int paginas;
    float precio;
};

enum Meses { ENE, FEB, MAR, ABR, MAY, JUN, JUL, AGO, SEP, OCT, NOV, DIC };

struct str_Revisa_Mensual
{
    char titulo[200];
    enum Meses mes;
    float precio;
};
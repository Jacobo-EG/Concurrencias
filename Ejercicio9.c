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

typedef struct str_Libro Libro;
typedef struct str_Revisa_Mensual Revista;

struct str_item
{
    Libro libro;
    Revista revista;
    char tipo;
};

typedef struct str_item Item;

int main(int argc, char const *argv[])
{
    Item tienda[100];
    int i=0;
    char car,elem;

    printf("Introduce el caracter i para insertar un nuevo elemento, o v para verlos todos. Finaliza con f");
    car = getchar();
    while(car!='f'){
        if(car=='i'){
            printf("Introduce r si se trata de una revista o l si se trata de un libro: ");
            elem=getchar();
            if(elem=='l'){
                tienda[i].tipo=elem;
                printf("Introduce el titulo del libro: ");
                scanf("%s",&tienda[i].libro.titulo);
                printf("Introduce el autor: ");
                scanf("%s",&tienda[i].libro.autor);
                printf("Introduce el numero de paginas: ");
                scanf("%i",&tienda[i].libro.paginas);
                printf("Introduce el precio: ");
                scanf("%f",&tienda[i].libro.precio);
                i++;
            }else if(elem=='r'){
                tienda[i].tipo=elem;
                printf("Introduce el titulo de la revista: ");
                scanf("%s",&tienda[i].revista.titulo);
                printf("Introduce el mes: ");
                scanf("%s",&tienda[i].revista.mes);
                printf("Introduce el precio: ");
                scanf("%f",&tienda[i].revista.precio);
                i++;
            }else{
                printf("No hay elemento de tipo: ");
                putchar(elem);
            }

        }else if(car=='v'){
            for(int j=0;j<i;j++){
                if(tienda[i].tipo=='l'){
                    printf("El elemento %i es un libro con titulo %s, del autor %s, con %i paginas y precio %f",j,tienda[j].libro.titulo,tienda[j].libro.autor,tienda[j].libro.paginas,tienda[j].libro.precio);
                }else{
                    printf("El elemento %i es una revista con titulo %s, del mes %s y con precio %f",tienda[j].revista.titulo,tienda[j].revista.mes,tienda[j].revista.precio);
                }
            }
        }else{
            printf("No hay accion definida para el caracter ");
            putchar(car);
        }
        printf("Introduce el caracter i para insertar un nuevo elemento, o v para verlos todos. Finaliza con f");
        car=getchar();
    }
    return 0;
}
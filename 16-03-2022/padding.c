#include <stdio.h>
#include <string.h>
#include <stdlib.h>
struct str_Libro1
{
    char titulo[7]; //7     /*Son bloques de 8, luego para que paginas no se quede entre dos bloques hace padding*/
    int paginas;    //4
    int capitulos;  //4
    char tipo;      //1
};

struct str_Libro2
{
    char titulo[7]; //7     /*Tenemos los bloques bien organizados de 8 en 8 y no hay necesidad de hacer padding por que no hay*/
    char tipo;      //1     /*variables que se encuentren en dos bloques.*/
    int paginas;    //4
    int capitulos;  //4
    
};

int main()
{
    /*Resultados diferentes de tamaño de estructura por el orden, esto es asi por el padding. Es mejor el segundo*/
    printf("char[7] %i 2*int %i char %i struct1 %i struct2 %i",7*sizeof(char),2*sizeof(int),sizeof(char),sizeof(struct str_Libro1),sizeof(struct str_Libro2));
    fflush(stdout);
    return 0;
}
/*
* syscall.c
*/
#include <stdio.h>
#include <unistd.h>

int main(int argvc, char * argv[]) {
    printf("Hola, mundo"); /* funcion C de E/S */
    write(1, "Adios, mundo ", 13); /* llamada al sistema, costosa, no buffer */
    return 0;
}
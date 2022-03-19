/*
 ============================================================================
 Name        : descifrar.c
 Author      : Jacobo Elicha Garucho
 Version     :
 Copyright   : Your copyright notice
 Description :
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

void decrypt(unsigned * v, unsigned * k){
    const unsigned delta = 0x9e3779b9;
    unsigned sum = 0xC6EF3720;
    for(int i=0;i<32;i++){
        (v[1])-=((v[0] << 4) + k[2])^(v[0]+sum)^((v[0] >> 5) + k[3]);
        (v[0])-=((v[1] << 4) + k[0])^(v[1]+sum)^((v[1] >> 5) + k[1]);
        sum-=delta;
    }
}

int main(int argc, char const *argv[])
{
    unsigned k[4]={128, 129, 130, 131}; 
    if(argc!=3){
        perror("Error, se requieren el nombre del fichero a desncriptar y el del desencriptado");
        exit(-1);
    }

    FILE * fe = fopen(argv[1],"rb");
    FILE * fd = fopen(argv[2],"wb");

    if(fe == NULL){
        perror("Error al abrir el fichero encriptado");
        exit(-1);
    }
    if(fd == NULL){
        perror("Error al abrir el fichero de desencriptado");
        exit(-1);
    }

    unsigned imgSize;
    if(fread(&imgSize,sizeof(unsigned),1,fe)==1){
        unsigned auxSize=imgSize;
        if(imgSize%8!=0) auxSize += 8-imgSize%8;    /*while(auxSize%8 !=0) auxSize++;*/

        unsigned * buffer = malloc(auxSize);
        fread(buffer,auxSize,1,fe);
        for(int i=0;i< auxSize/sizeof(unsigned);i+=2){
            decrypt(&buffer[i], k);
        }

        fwrite(buffer,sizeof(char),imgSize,fd);
    }
    

    fclose(fe);
    fclose(fd);
    
    return EXIT_SUCCESS;
}

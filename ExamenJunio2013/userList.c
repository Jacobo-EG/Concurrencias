#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "userList.h"

// typedef struct user {
//     int   uid_           ;
//     char *userName_      ;
//     char *homeDirectory_ ;
    
//     struct user * nextUser_ ;
//     struct user * previousUser_ ;
// } T_user ;

// typedef struct userList {
//     T_user * head_ ;
//     T_user * tail_ ;
//     int numberOfUsers_ ;
// } T_userList;

T_user * createUser(char *name, int uid, char *dir){

    T_user * nuevo = malloc(sizeof(T_user));

    if(nuevo == NULL){
        perror("No ha sido posible asignar memoria para crear el usuario \n");
        exit(-1);
    }

    (nuevo->userName_) = malloc(sizeof(name));
    (nuevo->homeDirectory_) = malloc(sizeof(dir));

    nuevo->uid_ = uid;
    strcpy((nuevo->userName_), name);
    strcpy((nuevo->homeDirectory_), dir);
    nuevo->nextUser_ = NULL;
    nuevo->previousUser_ = NULL;

    return nuevo;
}

T_userList createUserList(){
    T_userList* list = malloc(sizeof(T_userList));

    if(list == NULL){
        perror("No se ha podido asignar memoria para crear la lista \n");
        exit(-1);
    }

    list->numberOfUsers_ = 0;
    list->head_ = NULL;
    list->tail_ = NULL;

    return *list;
}

int addUser(T_userList * list, T_user* user){

    int ok = 1;
    T_user* ptr = (list->head_);

    while(ok == 1 && ptr != NULL){
        if(ptr->uid_== user->uid_|| strcmp(ptr->userName_,user->userName_)==0 ){    
            ok = 0;
        }
        ptr = ptr->nextUser_;
    }

    if(ok == 1){
        ptr = (list->head_);
        if(ptr == NULL){
            user->nextUser_ = NULL;
            user->previousUser_ = NULL;
            list->head_ = user;
            list->tail_ = user;  
        }else{
            user->nextUser_ = ptr;
            ptr->previousUser_ = user;
            user->previousUser_ = NULL;
            list->head_ = user;
        }
        list->numberOfUsers_++;
    }

    return ok;
}

int getUid(T_userList list, char *userName){
    int ok = -1;
    T_user* ptr = list.head_;
    
    while(ok == -1 && ptr != NULL){
            if(strcmp(ptr->userName_,userName)==0){
                ok = ptr->uid_;
            }
            ptr = ptr->nextUser_;
        }

    return ok;
}

int deleteUser(T_userList* list, char* userName){
    int ok = -1;
    T_user* ptr = list->head_;

    while(ok == -1 && ptr != NULL){
        if(strcmp(ptr->userName_,userName) == 0){
                ok = 0;
                if(list->numberOfUsers_ == 1){

                    list->numberOfUsers_ = 0;
                    list->head_ = NULL;
                    list->tail_ = NULL;

                    free(ptr);
                
                }else{

                    if(ptr->previousUser_ == NULL){
                        list->head_ = ptr->nextUser_;
                        ptr->nextUser_->previousUser_ = ptr->previousUser_;
                    }else if(ptr->nextUser_ == NULL){
                        list->tail_ = ptr->previousUser_;
                        ptr->previousUser_->nextUser_ = ptr->nextUser_;
                    }else{
                        ptr->nextUser_->previousUser_ = ptr->previousUser_;
                        ptr->previousUser_->nextUser_ = ptr->nextUser_; 
                    }

                    free(ptr);
                }
            }
            ptr = ptr->nextUser_;
    }

    return ok;
}

void printUserList(T_userList list, int reverse){
    if(reverse == 0){
        if(list.head_ == NULL){
            printf("--- Lista vacia --- \n");
        }
        while(list.head_ != NULL){
            printf("Nombre: %s, UID: %i, home directory: %s \n",list.head_->userName_,list.head_->uid_,list.head_->homeDirectory_);
            list.head_ = list.head_->nextUser_;
        }
    }else{
        if(list.tail_ == NULL){
            printf("--- Lista vacia --- \n");
        }
        while(list.tail_ != NULL){
            printf("Nombre: %s, UID: %i, home directory: %s \n",list.tail_->userName_,list.tail_->uid_,list.tail_->homeDirectory_);
            list.tail_ = list.tail_->previousUser_;
        }
    }
}
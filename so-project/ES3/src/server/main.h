#include <unistd.h> 
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>

// by fixing this value I can avoid using malloc and going for a simple int array
#define MAX_NUM_CLIENTS 256

int pid[MAX_NUM_CLIENTS];
int pid_n;

#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>

#define CLIENT_REQ "pipe_clientRequests"
#define BUFFER_SIZE 512

int req_d;
char buffer[BUFFER_SIZE];

int openPipe();
int writeInPipe(char* toBeWritten);
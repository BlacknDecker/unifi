#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>

#define CLIENT_REQ "req_pipe"
#define PIPE_READ_ERROR_MSG "ERROR: unable to read from pipe!"

// every write or read on a pipe will be this large
// this is for the sake of simplicity, I am not interested in efficiency
#define BUFFER_SIZE 512  
char buffer[BUFFER_SIZE];
int req_d;

int openReqPipe();
int writeInPipe(int pipe, char* toBeWritten);
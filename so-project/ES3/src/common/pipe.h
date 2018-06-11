#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <errno.h>
#include <signal.h>


#define CLIENT_REQ "req_pipe"
#define PIPE_READ_ERROR_MSG "ERROR: unable to read from pipe!"

#define BUFFER_SIZE 512

int createReqPipe();
int openReqPipe();
int createResPipe();
int openResPipe();
int writeInPipe(int pipe, char* toBeWritten);
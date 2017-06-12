#include "pipe.h"

#include <string.h>

//server
int createReqPipe()
{
	unlink(CLIENT_REQ); // remove named pipe if already exists
	printf("	unlink errno: %d\n",errno);
	errno = 0;

	mkfifo(CLIENT_REQ, 0666);
	printf("	mkfifo errno: %d\n",errno);

	printf("Almost done, waiting for a client to open req pipe...\n");
	int req_d = open (CLIENT_REQ, O_RDONLY);
	printf("	open descriptor: %d\n", req_d);

	return req_d;
}

//client
int openReqPipe() 
{
	int req_d;
	do {
		req_d = open(CLIENT_REQ, O_WRONLY);
		printf("	open request pipe: %d\n", req_d);
		if (req_d == -1) 
		{
			printf("	will try again\n");
			sleep(1); 
		}
	} while (req_d == -1);
	return req_d;
}

//server
int createResPipe(char* name)
{
	unlink(name);
	printf("	unlink errno: %d\n",errno);
	errno = 0;

	mkfifo(name, 0666);
	printf("	mkfifo errno: %d\n",errno);

	int res_d = open(name, O_WRONLY);
	printf("	open descriptor: %d\n", res_d);

	return res_d;
}

//client
int openResPipe()
{
	int req_d;
	do {
		char str[10];
		sprintf(str, "%d", getpid());
		req_d = open(str, O_RDONLY);
		printf("	open response pipe: %d\n", req_d);
		if (req_d == -1) 
		{
			printf("	will try again\n");
			sleep(1); 
		}
	} while (req_d == -1);
	return req_d;
}

int writeInPipe(int pipe, char* toBeWritten) 
{
	int error = write(pipe, toBeWritten, strlen(toBeWritten) * sizeof(char));
	sleep(1); //let the other process read command from from the pipe
	return error;
}
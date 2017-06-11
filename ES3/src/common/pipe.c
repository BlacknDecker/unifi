#include "pipe.h"

int openReqPipe() 
{
	int req_d;
	do {
		req_d = open(CLIENT_REQ, O_WRONLY);
		printf("	open descriptor: %d\n", req_d);
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
	int error = write(req_d, toBeWritten, BUFFER_SIZE);
	sleep(1); //let the other process read command from from the pipe
	return error;
}
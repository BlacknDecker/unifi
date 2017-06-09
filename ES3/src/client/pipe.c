#include "pipe.h"

int openPipe() 
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

int writeInPipe(char* toBeWritten) 
{
	return write(req_d, toBeWritten, sizeof(toBeWritten));
}
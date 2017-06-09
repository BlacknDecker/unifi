#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>

#define CLIENT_REQ "pipe_clientRequests"
#define BUFFER_SIZE 512

int req_d;
char buffer[BUFFER_SIZE];

int main() {
	printf("imma client\n");

	do {
		req_d = open(CLIENT_REQ, O_WRONLY);
		printf("	open descriptor: %d\n", req_d);
		if (req_d == -1) 
		{
			sleep (1); 
			printf("no\n");
		}
	} while (req_d == -1);

	printf("going to write\n");

	int i;
	for (i = 1; i <= 3; i++) {
		printf("going to write %ld\n", write(req_d, "4", 1));
		sleep (3); 
	}

}
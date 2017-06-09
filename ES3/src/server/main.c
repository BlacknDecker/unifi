#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h> 
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <errno.h>

#define CLIENT_REQ "pipe_clientRequests"
#define BUFFER_SIZE 512

int req_d;
char buffer[BUFFER_SIZE];

int main() {

	printf("Starting server...\n");

	unlink(CLIENT_REQ); // remove named pipe if already exists
	printf("	unlink errno: %d\n",errno);
	errno = 0;

	mkfifo(CLIENT_REQ, 0666);
	printf("	mkfifo errno: %d\n",errno);

	printf("Almost done, waiting for a client to open req pipe...\n");
	req_d = open (CLIENT_REQ, O_RDONLY);
	printf("	open descriptor: %d\n", req_d);

	printf("All set up. Server running...\n");

	// main server loop
	while (1)
	{
		if (read(req_d, buffer, BUFFER_SIZE)) 
		{
			int req_to_serve = atoi(buffer);	
				printf("serving %d\n", req_to_serve);

				if (req_to_serve==3) {
					if (read(req_d, buffer, BUFFER_SIZE))
						printf("%s\n", buffer);
					else
						printf("Something went terribly wrong...");
				}
		}
		else {
			printf("None is connected: nothing to do...\n");
			sleep(5);
		}

	}

}
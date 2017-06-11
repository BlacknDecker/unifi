#include "../common/pipe.h"
#include "main.h"

static void init()
{
	printf("Starting server...\n");

	pid_n = 0;

	unlink(CLIENT_REQ); // remove named pipe if already exists
	printf("	unlink errno: %d\n",errno);
	errno = 0;

	mkfifo(CLIENT_REQ, 0666);
	printf("	mkfifo errno: %d\n",errno);

	printf("Almost done, waiting for a client to open req pipe...\n");
	req_d = open (CLIENT_REQ, O_RDONLY);
	printf("	open descriptor: %d\n", req_d);

	printf("All set up. Server running...\n");
}

void req1() {
	if (read(req_d, buffer, BUFFER_SIZE))
	{
		int temp_pid = atoi(buffer);
		int already_there = 0;
		int i;

		for (i=0; i<pid_n; i++)
		{
			if (pid[i]==temp_pid)
			{
				already_there=1;
				printf("	client %d already logged!\n", temp_pid);
			}
		}

		if (!already_there)
		{
			pid[pid_n] = atoi(buffer);
			pid_n++;
			for (i=0; i<pid_n; i++)
				printf("	client n° %d logged in\n", pid[i]);
		}
	}
	else
		printf(PIPE_READ_ERROR_MSG);
}

void req4() {
	if (read(req_d, buffer, BUFFER_SIZE))
	{
		int temp_pid = atoi(buffer);
		int i, index = 0;

		for (i=0; i<pid_n; i++)
		{
			if (pid[i]==temp_pid)
			{
				pid[i] = 0;
				index=i+1;
				printf("	client n° %d logged out\n", temp_pid);
				break;
			}
		}

		if (index)
		{
			index--;

			for (; index<pid_n; index++)
			{
				pid[index] = pid[index+1];
			}
			pid_n--;
			printf("	cleaned process list\n");
		}
	}
	else
		printf(PIPE_READ_ERROR_MSG);
}

int main() 
{
	init();

	// main server loop
	while (1)
	{
		if (read(req_d, buffer, BUFFER_SIZE)) 
		{
			int req_to_serve = atoi(buffer);
			printf("asked for req n° %d\n", req_to_serve);

			if (req_to_serve ==1)
				req1();

			if (req_to_serve ==4)
				req4();

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
#include "../common/pipe.h"
#include "../common/custsignal.h"
#include "main.h"

static int init()
{
	printf("Starting server...\n");
	pid_n = 0;
	int req_d = createReqPipe();
	printf("All set up. Server running...\n");
	return req_d;
}

static void req1(int req_d)
{
	char buffer[BUFFER_SIZE];
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

static void req2(int req_d) 
{
	// read pid
	char buffer[sizeof(int)];
	if (read(req_d, buffer, sizeof(int)))
	{
		// signal the client about the pipe
		printf("	signal to %d ", atoi(buffer));
		printf("returned code: %d\n", kill(atoi(buffer), SIG_MSG));
		int res_d = createResPipe(buffer);

		char list[BUFFER_SIZE] = {'\0'};
		int error, i;

		if (pid_n <1)
		{
			strcpy(list, "No clients logged!\n");
		}

		for (i=0; i<pid_n; i++)
		{
			char temp[sizeof(int)];
			sprintf(temp, "%d ", pid[i]);
			strcat(list, temp);
		}

		strcat(list, "\n\0");

		error = writeInPipe(res_d, list);
		printf("	write in pipe returned code: %d\n", error);
	}
	else
		printf(PIPE_READ_ERROR_MSG);
}

static void req3(int req_d)
{
	char buffer[BUFFER_SIZE];
	if (read(req_d, buffer, BUFFER_SIZE))
	{
		char msg[BUFFER_SIZE];
		msg[0] = '\0';
		const char *start = strrchr(buffer, '_') + 1;
		strncat(msg, start, strcspn(start, "#"));

		char sender[10];
		sender[0] = '\0';
		const char *s = strrchr(buffer, '+') + 1;
		strncat(sender, s, strcspn(s, "*"));

		while (strchr(buffer, '_'))
		{
			// exrtact the destinatary from buffer string
			char dest[10];
			dest[0] = '\0';
			strncat(dest, buffer, strcspn(start, "_")-1);

			//strip away the destinatary taken
			const char *start = strchr(buffer, '_') + 1;
			char new_buffer[BUFFER_SIZE];
			new_buffer[0] = '\0';
			strncat(new_buffer, start, strcspn(start, "#"));
			strcpy(buffer, new_buffer);

			// check if destinatary exists
			int i, ok = 0;
			for (i=0; i<pid_n; i++)
			{
				if (pid[i]==atoi(dest))
					ok = 1;
			}

			if (ok)
			{
				printf("	signal to %d ", atoi(dest));
				printf("returned code: %d\n", kill(atoi(dest), SIG_MSG));

				//this may not be elegant, but it wil clean dest pid from dirty chars
				sprintf(dest, "%d", atoi(dest));
				int res_d = createResPipe(dest);

				int error = writeInPipe(res_d, msg);
				printf("	write in pipe returned code: %d\n", error);
			}
			else
			{
				printf("	signal to %d ", atoi(sender));
				printf("returned code: %d\n", kill(atoi(sender), SIG_N_EX));
			}
		}

	}
	else
		printf("Something went terribly wrong...");
}

static void req4(int req_d) 
{
	char buffer[BUFFER_SIZE];
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
	int req_d = init();
	
	// main server loop
	while (1)
	{
		char buffer[1];
		if (read(req_d, buffer, 1))
		{
			int req_to_serve = atoi(buffer);
			printf("asked for req n° %d\n", req_to_serve);

			if (req_to_serve ==1)
				req1(req_d);

			if (req_to_serve ==2)
				req2(req_d);

			if (req_to_serve ==3) 
				req3(req_d);

			if (req_to_serve ==4)
				req4(req_d);
		}
		else {
			printf("None is connected: nothing to do...\n");
			sleep(3);
		}
	}
}

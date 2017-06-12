#include "../common/custsignal.h"
#include "interact.h" // for macros

static void msgHandler(int sig)
{
	char msg[BUFFER_SIZE];

	char c_pid[10];
	sprintf(c_pid, "%d", getpid());

	int req_d = openResPipe();

	if (read(req_d, msg, BUFFER_SIZE))
	{
		printf("\n%s\n",msg);
	}
}

static void destinataryHandler(int sig)
{

}

static void closeHandler(int sig) 
{
	printf("Client is closing. Disconnecting...");
	char cmd[] = "4";
	int error = writeInPipe(req_d, cmd);
	printf("	write returned code: %d\n", error);
	char c_pid[10];
	sprintf(c_pid, "%d", getpid());
	error = writeInPipe(req_d, c_pid);
	printf("	write returned code: %d\n", error);
	exit(0);
}

void setupSignals(int r)
{
	req_d = r;
	printf("...setting up signals...\n");
	signal(SIG_MSG, msgHandler);
	signal(SIG_N_EX, destinataryHandler);
	signal(SIGINT, closeHandler);
	printf("...done...\n");
}

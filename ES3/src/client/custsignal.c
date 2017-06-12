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
	printf("you are closing me");

}

void setupSignals()
{
	printf("...setting up signals...\n");
	signal(SIG_MSG, msgHandler);
	signal(SIG_N_EX, destinataryHandler);
	signal(SIGINT, closeHandler);
	printf("...done...\n");
}

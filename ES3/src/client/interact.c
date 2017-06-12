#include  "interact.h"

void printGreetings() 
{
	printf("CLIENT PROCESS v1.0 - Simone Cipriani\n");
}

void printMenu() 
{
	printf("\nSelect one of the following options: \n");
	printf("	[1] : connect to server\n");
	printf("	[2] : requests client list\n");
	printf("	[3] : send text message to other clients\n");
	printf("	[4] : disconnect from server\n");
	printf("	[5] : exit program\n");
}

static void message(int req_d) 
{
	char dest[10];
	char msg[BUFFER_SIZE];
	int error;

	printf("Please insert your message:\n");
	scanf( "%s", msg );
	error = writeInPipe(req_d, msg);
	printf("	write in pipe returned code: %d\n", error);

	int id = 1;
	while (id) 
	{
		printf("Please insert destinataries [0 = stop]: \n");
		scanf( "%s", dest );
		error = writeInPipe(req_d, dest);
		printf("	write returned code: %d\n", error);
		id = atoi(dest);
	}
}

static void readIdList(int req_d)
{
	//pass pid to server
	char c_pid[10];
	sprintf(c_pid, "%d", getpid());
	int error = writeInPipe(req_d, c_pid);
	printf("	write returned code: %d\n", error);

	//server will rise signal
}

void dispatchCmd(int req_d) 
{
	char c_cmd[1];
	int error;

	scanf( "%s", c_cmd );

	// handle immediately close action
	int i_cmd = atoi(c_cmd);
	if (i_cmd == 5) 
	{
		kill(getpid(), SIGINT);
		return;
	}

	error = writeInPipe(req_d, c_cmd);
	printf("	write returned code: %d\n", error);

	//log in, log out
	if (i_cmd == 1 || i_cmd == 4)
	{
		char c_pid[10];
		sprintf(c_pid, "%d", getpid());
		error = writeInPipe(req_d, c_pid);
		printf("	write returned code: %d\n", error);
	}

	if (i_cmd == 2)
		readIdList(req_d);

	if (i_cmd == 3)
		message(req_d);
}
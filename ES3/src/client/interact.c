#include  "interact.h"

void printGreetings() {
	printf("CLIENT PROCESS v1.0 - Simone Cipriani\n");
}

void printMenu() {
	printf("Select one of the following options: \n");
	printf(" ...\n");
}

int dispatchCmd() {
	char c_cmd[1];
	char msg[BUFFER_SIZE-1];

	scanf( "%s", c_cmd );

	int i_cmd = atoi(c_cmd);

	if (i_cmd == 3)
	{
		printf("Please insert your message:\n");
		scanf( "%s", msg );
	}

	char *inPipe = strcat(c_cmd, msg);
	return writeInPipe(inPipe);
}
#include  "interact.h"

void printGreetings() {
	printf("CLIENT PROCESS v1.0 - Simone Cipriani\n");
}

void printMenu() {
	printf("Select one of the following options: \n");
	printf(" ...\n");
}

void dispatchCmd() {
	char c_cmd[1];
	char msg[BUFFER_SIZE-1];
	int error;

	scanf( "%s", c_cmd );
	error = writeInPipe(c_cmd);
	printf("	write returned code: %d\n", error);

	int i_cmd = atoi(c_cmd);

	if (i_cmd == 3)
	{
		printf("Please insert your message:\n");
		scanf( "%s", msg );
		writeInPipe(msg);
		printf("	write returned code: %d\n", error);
	}
}
#include <stdio.h>

#include "globals.h"
#include "list.h"
#include "cmd.h"
#include "io.h"

int main() { 

	printf("Scheduler Simulator v1.0 - Simone Cipriani\n\n");

	while(1) {
		printCmds();
		break;
	}

	int iError;

	iError = initTaskList(LIST_INIT_LEN);	
	if (iError) 	{
		printf("ERROR: could not initialize Task list!");
		return iError;
	}

	
	char name[10] = "0123456789";
	insertTask(666, name, 7);
	insertTask(666, name, 7);
	insertTask(666, name, 7);
	insertTask(666, name, 7);




	printf("%d",tasks[3].iId);


	printf("  %d", (int) sizeof(tasks));


	return 0;
};
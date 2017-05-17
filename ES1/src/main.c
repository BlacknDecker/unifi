#include "globals.h"
#include "list.h"
#include "cmd.h"
#include "io.h"

int main() { 

	printProgHead();

// init list
	int iError;
	iError = initTaskList(LIST_INIT_LEN);	
	if (iError)		
		return iError;

	//default policy is priority
	policy = malloc(sizeof(Policy));
	*policy = PRIORITY; 
	
	//main program loop
	while(1) {
		printCmds();
		iError = dispatchCMD();
		if (iError!=EXIT_SUCCESS)
			return iError;
		sortList();
		printTaskList();
	}


	char name[10] = "0123456789";
	insertTask(666, name, 7);
	insertTask(666, name, 7);
	insertTask(666, name, 7);
	insertTask(666, name, 7);




	//printf("%d",tasks[3].iId);


	//printf("  %d", (int) sizeof(tasks));


	return 0;
};
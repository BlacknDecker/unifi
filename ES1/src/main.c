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
	policy = SJF; 
	
	//main program loop
	while(1) {
		printCmds();
		iError = dispatchCMD();
		if (iError!=EXIT_SUCCESS)
			return iError;
		sortList();
		printTaskList();
	}

	return 0;
};
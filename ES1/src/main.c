#include "headers/globals.h"
#include "headers/list.h"
#include "headers/io.h"

#include <stdio.h>

int main() { 

	printProgHead();

	// init list
	int i_ret_code;
	i_ret_code = initTaskList(LIST_INIT_LEN);	
	if (i_ret_code!=EXIT_SUCCESS)
		return i_ret_code;

	//default policy is priority
	i_policy = PRIORITY; 
	
	//main program loop
	while(1) {
		printCmds();
		i_ret_code = dispatchCMD();
		if (i_ret_code!=EXIT_SUCCESS)
			return i_ret_code;
		sortList();
		printTaskList();

		printf("%d",i_num_task);
	}

	//should never reach here, but in case..
	return EXIT_FAILURE;
};
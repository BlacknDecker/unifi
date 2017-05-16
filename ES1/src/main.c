#include "globals.h"

int main() { 

	//initialize tasks array
	taskSize = 3;
	tasks = malloc(taskSize * sizeof(tasks));

	//enlarge array
	taskSize += 5;

	Task* reallocTask = realloc(tasks, taskSize * sizeof(Task));
	if (reallocTask) 
		tasks = reallocTask;
	else {
     // deal with realloc failing because memory could not be allocated.
	}

	return 0;
};



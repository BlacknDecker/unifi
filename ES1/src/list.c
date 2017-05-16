#include "globals.h"

#include <stdio.h> //debug

int initTaskList(int len) 
{
	iAssignId = 1;

	taskSize = len;
	iNumTasks = 0;
	tasks = malloc(len * sizeof(Task));
	if (tasks)
		return EXIT_SUCCESS;
	else
		return EXIT_FAILURE;
}

int alterListSize(int len) 
{
	taskSize += len;
	Task* reallocTask = realloc(tasks, taskSize * sizeof(Task));
	if (reallocTask) {
		tasks = reallocTask;
		return EXIT_SUCCESS;
	}
	else 
		return EXIT_FAILURE;
}
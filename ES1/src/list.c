#include <stdio.h> //debug
#include "globals.h"

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

int compare(Task *a, Task *b) {
	//TODO: check with specs
	if (*policy==PRIORITY)
		return (a->iPriority>b->iPriority);
	if (*policy==SJF)
		return (a->iRemCycles<b->iRemCycles);
	//default case, should never happen
	return 0;
}

//bubble sort, terrible in performances but easy to implement
void sortList() {
	int i,k;
	Task temp;
	for(i = 0; i<iNumTasks-1; i++) {
		for(k = 0; k<iNumTasks-1-i; k++) {
			if (compare(&tasks[k],&tasks[k+1]))
			{
				temp = tasks[k];
				tasks[k] = tasks[k+1];
				tasks[k+1] = temp;
			}
		}
	}
}

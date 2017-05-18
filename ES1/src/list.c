#include <stdio.h> //debug
#include "globals.h"

int initTaskList(int len) 
{
	iAssignId = 1;
	iNumTasks = 0;
	taskSize = len;

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
		free(tasks);
		tasks = reallocTask;
		return EXIT_SUCCESS;
	}
	else 
		return EXIT_FAILURE;
}

int garbageCollectList() {
	return 0;
}

int compare(Task *a, Task *b) {

	if (*policy==PRIORITY)
	{
		if (a->iPriority==b->iPriority)
			return (a->iId<b->iId);
		else
			return (a->iPriority<b->iPriority);
	}

	if (*policy==SJF)
	{
		if (a->iPriority==b->iPriority)
			return (a->iId<b->iId);
		else
			return (a->iRemCycles>b->iRemCycles);
	}

	//default case, should never happen
	return 0;
}

//bubble sort, terrible in performances but easy to implement
void sortList() {
	int i,k;
	Task temp;
	for(i = 0; i<taskSize-1; i++) {
		for(k = 0; k<taskSize-1-i; k++) {
			if (compare(&tasks[k],&tasks[k+1]))
			{
				temp = tasks[k];
				tasks[k] = tasks[k+1];
				tasks[k+1] = temp;
			}
		}
	}
}

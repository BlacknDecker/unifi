#include <string.h>

#include "globals.h"
#include "list.h"
#include "cmd.h"

int insertTask(int priority, char name[], int exec) 
{
	Task t;

	t.iId = iAssignId;
	iAssignId++;
	t.iPriority = priority;
	strncpy(t.cName, name, 8); // any character beyond 8th will be ignored
	t.iRemCycles = exec;

	if (taskSize > iNumTasks) {
		tasks[iNumTasks] = t;
		iNumTasks++;
		return EXIT_SUCCESS;
	}
	else {
		int iError = alterListSize(LIST_ENLARGE_STEP);
		tasks[iNumTasks] = t;
		iNumTasks++;
		return iError;
	}
}

int execTopTask() 
{
	tasks[iNumTasks-1].iRemCycles--;

	if (tasks[iNumTasks-1].iRemCycles <= 0)
		return deleteTask(tasks[iNumTasks-1].iId);
	else return EXIT_SUCCESS;
}

int execTask(int id) {
	int i;
	for (i=0; i<iNumTasks; i++)
	{
		if (tasks[i].iId == id)
		{
			tasks[i].iRemCycles--;
			if (tasks[i].iRemCycles <= 0)
				return deleteTask(tasks[i].iId);
			else return EXIT_SUCCESS;
		}
	}
	//if id does not exist, simply do nothing
	return EXIT_FAILURE;
}

int deleteTask(int id) {
	int k;
	for(k = 0; k<iNumTasks-1; k++) {
		if (tasks[k].iId == id)
		{
			tasks[k].iPriority = -1;
			tasks[k].iRemCycles = -1;
		}
	}
	sortList();
	iNumTasks--;
	return garbageCollectList();
}

int alterPriority(int id, int newPriority) 
{
	int k;
	for(k = 0; k<iNumTasks-1; k++) {
		if (tasks[k].iId == id)
		{
			tasks[k].iPriority = newPriority;
			return EXIT_SUCCESS;
		}
	}
	return EXIT_FAILURE;
}

int switchSchedulingPolicy() 
{
	if (*policy == PRIORITY)
		*policy = SJF;
	if (*policy == SJF)
		*policy = PRIORITY;

	return EXIT_SUCCESS;
}
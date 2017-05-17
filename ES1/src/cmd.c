#include <string.h>

#include "globals.h"
#include "list.h"

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
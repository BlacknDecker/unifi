#include <stdio.h>
#include "cmd.h"
#include "globals.h"

void printCmds() 
{
	printf("Enter one of the following commands:\n");
	printf("	1 [insert new task]\n");
	printf("	2 [execute first-in-queue task]\n");
	printf("	3 [execute specified task]\n");
	printf("	4 [delete specified task]\n");
	printf("	5 [alter specified task's priority]\n");
	printf("	6 [switch scheduling policy]\n");
	printf("	7 [exit]\n");
}

#define SEPARATOR "+----+----------+------+-----------------+\n"

void printTaskList() 
{
	printf(SEPARATOR);
	printf("| ID | PRIORITY | NAME | REMAINING EXEC. |\n");
	printf(SEPARATOR);
	int i =0;
	for (i=0; i<iNumTasks; i++)
	{
		printf("| %d", tasks->iId);
		printf(" | %d", tasks->iPriority);
		printf(" | %s", tasks->cName);
		printf(" | %d", tasks->iRemCycles);
		printf(" |\n");
		printf(SEPARATOR);
	}
}

int dispatchCMD() 
{
	int iCMD;
	scanf( "%d", &iCMD );

	if (iCMD==1)
	{
		int iPriority, iExec;
		char cName[8];

		printf("Insert task priority:\n");
		scanf( "%d", &iPriority );

		printf("Insert task name:\n");
		scanf("%s", &cName);

		printf("Insert remaining executions:\n");
		scanf( "%d", &iExec );

		return insertTask(iPriority, cName,  iExec);
	}

//handle this
	return 1;

}
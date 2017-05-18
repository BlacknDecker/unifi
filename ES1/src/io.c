#include <stdio.h>
#include "cmd.h"
#include "globals.h"

void printProgHead()
{
	printf("\n*** Scheduler Simulator v1.0 - Simone Cipriani ***\n\n");
}

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
		//is indentation required?
		printf("| %d", tasks[i].iId);
		printf(" | %d", tasks[i].iPriority);
		printf(" | %s", tasks[i].cName);
		printf(" | %d", tasks[i].iRemCycles);
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

	if(iCMD==2)
		return execTopTask();

	if(iCMD==3)
	{
		int iId;
		printf("Insert task id:\n");
		scanf( "%d", &iId );
		return execTask(iId);
	}


	if(iCMD==4)
	{
		int iId;
		printf("Insert task id:\n");
		scanf( "%d", &iId );
		return deleteTask(iId);
	}

	if(iCMD==5)
	{
		int iId, iPriority;
		printf("Insert task id:\n");
		scanf( "%d", &iId );
		printf("Insert task priority:\n");
		scanf( "%d", &iPriority );

		return alterPriority(iId, iPriority);
	}

	if(iCMD==6)
	{
		if (switchSchedulingPolicy())
		{
			if (*policy == PRIORITY)
				printf("Scheduling policy now set to PRIORITY\n");
			if (*policy == SJF)
				printf("Scheduling policy now set to SHORTEST JOB FIRST\n");

			return EXIT_SUCCESS;
		}
	}

	if(iCMD==7)
		exit(0);

//unknown command
	return 0;

}
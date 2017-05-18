#include <stdio.h>

#include "headers/globals.h"
#include "headers/cmd.h"

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

#define SEPARATOR "+----+----------+-------+----------+\n"

void printTaskList() 
{
	printf("\n");
	printf(SEPARATOR);
	printf("| ID | PRIORITY | EXEC. |   NAME   |\n");
	printf(SEPARATOR);
	int i =0;
	for (i=0; i<i_num_task; i++)
	{
		printf("| %d", task_list[i].i_id);

		if (task_list[i].i_id < 10)
			printf("  |");
		else 
			printf(" |");

		printf(" %d", task_list[i].i_priority);

		printf("        | %d", task_list[i].i_cycles);
		if (task_list[i].i_cycles < 10)
			printf("     ");
		else 
			printf("    ");

		printf("| %s", task_list[i].c_name);

		printf("\n");

		printf(SEPARATOR);
	}
	printf("\n");
}

int dispatchCMD() 
{
	int i_cmd;
	scanf( "%d", &i_cmd );

	if (i_cmd==1)
	{
		int i_priority, i_exec;
		char c_name[8];

		printf("Insert task priority:\n");
		scanf( "%d", &i_priority );
		if (i_priority > 9 || i_priority < 0)
		{
			printf("ERROR: please inster a number between 0 and 9!\n");
			return EXIT_SUCCESS;
		}

		printf("Insert task name:\n");
		scanf("%s", &c_name);

		printf("Insert remaining executions:\n");
		scanf( "%d", &i_exec );

		if (insertTask(i_priority, c_name,  i_exec)!=EXIT_SUCCESS)
		{
			printf("ERROR: could not insert task!");
			printf("Probably something has gone very bad with memory, the program is closing.");
			return EXIT_FAILURE;
		}
		return EXIT_SUCCESS;

	}

	if(i_cmd==2)
	{
		if (execTopTask() !=EXIT_SUCCESS)
		{
			printf("ERROR: could not execute first-in-queue task!");
			printf("Probably something has gone very bad with memory, the program is closing.");
			return EXIT_FAILURE;
		}
		return EXIT_SUCCESS;
	}

	if(i_cmd==3)
	{
		int i_id;
		printf("Insert task id:\n");
		scanf( "%d", &i_id );
		if (execTask(i_id) !=EXIT_SUCCESS)
		{
			printf("ERROR: could not execute specified task!");
			return EXIT_SUCCESS;
		}
		return EXIT_SUCCESS;

	}

	if(i_cmd==4)
	{
		int i_id;
		printf("Insert task id:\n");
		scanf( "%d", &i_id );
		if (deleteTask(i_id) !=EXIT_SUCCESS)
		{
			printf("ERROR: could not delete specified task!");
			return EXIT_SUCCESS;
		}
		return EXIT_SUCCESS;
	}

	if(i_cmd==5)
	{
		int i_id, i_priority;
		printf("Insert task id:\n");
		scanf( "%d", &i_id );
		printf("Insert task priority:\n");
		scanf( "%d", &i_priority );
		if (alterPriority(i_id, i_priority) !=EXIT_SUCCESS)
		{
			printf("ERROR: could not alter priority of specified task!");
			return EXIT_SUCCESS;
		}
		return EXIT_SUCCESS;
	}

	if(i_cmd==6)
	{
		if (switchSchedulingPolicy() == EXIT_SUCCESS)
		{
			if (i_policy == PRIORITY)
				printf("\nScheduling policy now set to PRIORITY\n");
			if (i_policy == LJF)
				printf("\nScheduling policy now set to LONGEST JOB FIRST\n");

			return EXIT_SUCCESS;
		}
	}

	if(i_cmd==7)
		exit(0);

	//unknown command
	printf("Unknown command. Please enter a valid one:\n");
	return dispatchCMD();
}
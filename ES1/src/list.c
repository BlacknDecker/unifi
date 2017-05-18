#include "headers/globals.h"

#include <stdio.h>

int initTaskList(int i_len) 
{
	i_assign_id = 1;
	i_num_task = 0;
	task_size = i_len;

	task_list = malloc(i_len * sizeof(task_t));
	if (task_list)
		return EXIT_SUCCESS;
	else
		return EXIT_FAILURE;
}

int alterListSize(int i_len) 
{
	task_size += i_len;
	task_t* realloc_task = realloc(task_list, task_size * sizeof(task_t));
	if (realloc_task) 
	{
		free(task_list);
		task_list = realloc_task;
		return EXIT_SUCCESS;
	}
	else 
		return EXIT_FAILURE;
}

int releaseEmptySpace() 
{
	task_t* clean_task_list = malloc( (i_num_task +1) * sizeof(task_t));

	if (!task_list)
		return EXIT_FAILURE;

	int i;
	for (i=0; i<task_size; i++)
		clean_task_list[i] = task_list[i];
	
	free(task_list);
	task_list = clean_task_list;
	task_size = i_num_task + 1;

	return EXIT_SUCCESS;
}

int compare(task_t a, task_t b) 
{
	if (i_policy==PRIORITY)
	{
		if (a.i_priority==b.i_priority)
			return (a.i_id<b.i_id);
		else
			return (a.i_priority<b.i_priority);
	}

	if (i_policy==LJF)
	{
		if (a.i_cycles==b.i_cycles)
			return (a.i_id<b.i_id);
		else
			return (a.i_cycles>b.i_cycles);
	}

	//default case, should never happen
	return 0;
}

//bubble sort, terrible in performances but easy to implement
void sortList() 
{
	int i,k;
	task_t temp;
	for(i = 0; i<i_num_task-1; i++) 
	{
		for(k = 0; k<i_num_task-1-i; k++) 
		{
			if (compare(task_list[k],task_list[k+1]))
			{
				temp = task_list[k];
				task_list[k] = task_list[k+1];
				task_list[k+1] = temp;
			}
		}
	}
}

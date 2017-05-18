#include <string.h>

#include "headers/globals.h"
#include "headers/list.h"
#include "headers/cmd.h"

int insertTask(int i_p, char c_n[], int i_ex) 
{
	task_t t;

	t.i_id = i_assign_id;
	i_assign_id++;
	t.i_priority = i_p;
	strncpy(t.c_name, c_n, 8); // any character beyond 8th will be ignored
	t.i_cycles = i_ex;

	if (task_size > i_num_task) {
		task_list[i_num_task] = t;
		i_num_task++;
		return EXIT_SUCCESS;
	}
	else {
		int iError = alterListSize(LIST_ENLARGE_STEP);
		task_list[i_num_task] = t;
		i_num_task++;
		return iError;
	}
}

int execTopTask() 
{
	task_list[i_num_task-1].i_cycles--;

	if (task_list[i_num_task-1].i_cycles <= 0)
		return deleteTask(task_list[i_num_task-1].i_id);
	else return EXIT_SUCCESS;
}

int execTask(int i_task_id) 
{
	int i;
	for (i=0; i<i_num_task; i++)
	{
		if (task_list[i].i_id == i_task_id)
		{
			task_list[i].i_cycles--;
			if (task_list[i].i_cycles <= 0)
				return deleteTask(task_list[i].i_id);
			else return EXIT_SUCCESS;
		}
	}
	//if id does not exist, simply do nothing 
	//and let cmd dispatcher print an error msg
	return EXIT_FAILURE;
}

int deleteTask(int i_task_id) 
{
	int k;
	for(k = 0; k<i_num_task; k++) {
		if (task_list[k].i_id == i_task_id)
		{
			task_list[k].i_id = 0;
			task_list[k].i_priority = 0;
			strncpy(task_list[k].c_name, "", 8);
			task_list[k].i_cycles = 0;
		}
	}
	i_num_task--;
	return releaseEmptySpace();
}

int alterPriority(int i_task_id, int i_new_p) 
{
	int k;
	for(k = 0; k<i_num_task-1; k++) {
		if (task_list[k].i_id == i_task_id)
		{
			task_list[k].i_priority = i_new_p;
			return EXIT_SUCCESS;
		}
	}
	return EXIT_FAILURE;
}

int switchSchedulingPolicy() 
{
	if (i_policy == PRIORITY)
		i_policy = LJF;
	else if (i_policy == LJF)
		i_policy = PRIORITY;

	return EXIT_SUCCESS;
}
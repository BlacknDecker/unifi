#include <stdlib.h>

#define LIST_INIT_LEN 5
#define LIST_ENLARGE_STEP 5

typedef struct {
	int i_id;
	int i_priority;
	char c_name[8];
	int i_cycles;
} task_t;

task_t* task_list;
size_t task_size;
int i_num_task;

int i_assign_id;

#define PRIORITY 1
#define LJF 0
int i_policy;
int insertTask(int priority, char name[], int exec);
int execTopTask();
int execTask(int id);
int deleteTask(int id);
int alterPriority(int id, int newPriority);
int switchSchedulingPolicy();
void exit();
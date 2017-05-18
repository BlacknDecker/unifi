#include <stdlib.h>

#define LIST_INIT_LEN 3
#define LIST_ENLARGE_STEP 3

typedef struct {
	int iId;
	int iPriority;
	char cName[8];
	int iRemCycles;
} Task;

Task* tasks;
size_t taskSize;
int iNumTasks;

int iAssignId;

#define PRIORITY 1
#define SJF 0
int policy;
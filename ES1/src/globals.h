#include <stdlib.h>

#define LIST_INIT_LEN 5
#define LIST_ENLARGE_STEP 5

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

typedef enum {
	PRIORITY, SJF
} Policy;

Policy* policy;
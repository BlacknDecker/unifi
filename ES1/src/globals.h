#include <stdlib.h>

typedef struct {
	int iId;
	int iPriority;
	char cName[8];
	int iRemCycles;
} Task;

size_t taskSize;
Task* tasks;
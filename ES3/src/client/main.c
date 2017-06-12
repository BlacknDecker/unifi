#include "interact.h"
#include "../common/custsignal.h"

int main() {
	printGreetings();
	setupSignals();
	int req_d = openReqPipe();

	while (1) 
	{
		printMenu();
		dispatchCmd(req_d);
	}
}
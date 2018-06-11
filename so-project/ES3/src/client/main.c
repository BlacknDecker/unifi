#include "interact.h"
#include "../common/custsignal.h"

int main() {
	printGreetings();
	int req_d = openReqPipe();
	setupSignals(req_d);

	while (1) 
	{
		printMenu();
		dispatchCmd(req_d);
	}
}
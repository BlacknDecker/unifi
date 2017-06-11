#include "interact.h"

int main() {
	printGreetings();
	req_d = openReqPipe();

	while (1) 
	{
		printMenu();
		dispatchCmd();
	}

}
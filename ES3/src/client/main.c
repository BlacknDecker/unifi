#include "interact.h"

#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>

int main() {
	printGreetings();
	req_d = openPipe();

	while (1) 
	{
		printMenu();
		dispatchCmd();
	}

}
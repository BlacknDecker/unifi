#include <stdio.h>
#include <stdlib.h>

#define BUFFERSIZE 512

char buffer[BUFFERSIZE];

int main (int argc, char *argv[])
{
	while(fgets(buffer, BUFFERSIZE , stdin) != NULL)
	{
		system(buffer);

		// exit when empty line
		if (buffer[0]=='\n')
			return 0;
	}
	return 0;
}
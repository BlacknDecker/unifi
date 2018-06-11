#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFFERSIZE 512

char buffer[BUFFERSIZE];
int out_num;

int main (int argc, char *argv[])
{
	out_num = 1;

	while(fgets(buffer, BUFFERSIZE , stdin) != NULL)
	{
		// exit if empty line
		if (buffer[0]=='\n')
			return 0;

		// prepare std out redirection
		char to_file[BUFFERSIZE];
		snprintf(to_file, sizeof to_file, ">  out.%d", out_num);
		out_num++;

		// remove newline and form complete shell command
		strtok(buffer, "\n");
		strcat(buffer, to_file);

		// rock it!
		system(buffer);
		
	}
	return 0;
}
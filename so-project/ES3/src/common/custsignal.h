#include <signal.h>

#define SIG_MSG SIGUSR1
#define SIG_N_EX SIGUSR2

int req_d; // need this to catch ctrl-c closing

void setupSignals();
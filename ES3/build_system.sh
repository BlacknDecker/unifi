#!/bin/sh

# clean
rm -rf bin
mkdir bin

# compile commons
cc -c src/common/pipe.c -o bin/pipe.o

# compile server
cc -c src/server/main.c -o bin/server.o

# link server
cc bin/server.o  bin/pipe.o -o bin/server

# compile client
cc -c src/client/main.c -o bin/cmain.o
cc -c src/client/interact.c -o bin/cinteract.o
cc -c src/client/custsignal.c -o bin/csignal.o

# link client
cc bin/cmain.o bin/cinteract.o bin/csignal.o bin/pipe.o -o bin/client

# clean folder from object files
rm bin/*.o

# run everything
./run.sh
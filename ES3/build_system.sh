#!/bin/sh

# clean
rm -rf bin
mkdir bin

# compile and link server
cc src/server/main.c -o bin/server

# compile and link client
cc src/client/main.c src/client/interact.c src/client/pipe.c -o bin/client

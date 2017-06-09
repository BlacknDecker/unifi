#!/bin/sh

# clean
rm -rf bin
mkdir bin

# compile and link server
cc src/server/main.c -o bin/server

# compile and link client
cc src/client/main.c -o bin/client

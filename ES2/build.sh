#!/bin/sh

# clean
rm -rf bin
mkdir bin

# compile
cc -c src/main.c -o bin/main.o

# link
cc bin/main.o -o bin/main

#run
cat some_inputs.txt | ./bin/main
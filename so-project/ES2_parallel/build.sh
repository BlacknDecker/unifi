#!/bin/sh

# clean
rm -rf bin
mkdir bin

# compile
cc -c src/main.c -o bin/main.o

# link
cc bin/main.o -o bin/main

# run
cd bin	# output is generated in current dir
cat ../some_inputs.txt | ./main
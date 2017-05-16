#!/bin/sh

# clean
rm -rf bin
mkdir bin

# compile
cc -c src/main.c -o bin/main.o
cc -c src/list.c -o bin/list.o
cc -c src/cmd.c -o bin/cmd.o
cc -c src/io.c -o bin/io.o

# link
cc bin/main.o bin/list.o bin/cmd.o bin/io.o -o bin/main

# run
./bin/main
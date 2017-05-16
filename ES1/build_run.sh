#!/bin/sh

# clean
rm -rf bin
mkdir bin

# compile
cc src/main.c -o bin/main

# run
./bin/main
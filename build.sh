#!/usr/bin/env bash

find src/ -name "*.java" > source.txt
javac -d build @source.txt


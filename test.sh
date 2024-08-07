#!/usr/bin/env bash

find test/ -name "*.java" > source-test.txt
#javac -d build/test -cp libs/junit-jupiter-api-5.10.2.jar:libs/junit-jupiter-engine-5.10.2.jar:build @source-test.txt

javac -d build/test -cp libs/junit-platform-console-standalone-1.9.3.jar:build @source-test.txt

java -jar libs/junit-platform-console-standalone-1.9.3.jar -cp build/test/:build --select-package com.lapchenko.generator.parser

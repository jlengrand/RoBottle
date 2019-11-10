#!/bin/sh

# generate header
cd src/main/java;javac -h . HelloWorldJNI.java;cd -;

# create library
cd src/main/c; make library; cd -

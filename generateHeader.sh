#!/bin/sh

# generate header
cd src/main/java;javac -h . driver.Dht11Driver.java;cd -;

# create library
cd src/main/c; make library; cd -

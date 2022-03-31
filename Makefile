.PHONY: all test clean

sources = $(shell find src -name "*.java")

all: clean run

run: src/*.java
	javac $(sources) -d class -Xlint:unchecked
	java -cp class:lib/ojdbc.jar Main

clean: 
	rm -rf class/*

test: src/*.java test/*.java
	javac {src,src/**,test}/*.java -d class -cp lib/junit.jar
	java -jar lib/junit.jar -cp class/ --scan-classpath

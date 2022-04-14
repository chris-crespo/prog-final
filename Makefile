.PHONY: all test clean

sources = $(shell find src -name "*.java")
all = $(shell find src test -name "*.java")

all: clean run

run: src/*.java
	javac $(sources) -d class -Xlint:unchecked
	java -cp class:lib/postgresql.jar Main

clean: 
	rm -rf class/*

test: src/*.java test/*.java
	javac $(all) -d class -cp lib/junit.jar
	java -jar lib/junit.jar -cp class/ --scan-classpath

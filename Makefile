.PHONY: all test clean

all: clean run

run: src/*.java
	javac {src/**,src}/*.java -d class -Xlint:unchecked
	java -cp class Main

clean: 
	rm -rf class/*

test: src/*.java test/*.java
	javac {src,src/**,test}/*.java -d class -cp lib/junit.jar
	java -jar lib/junit.jar -cp class/ --scan-classpath

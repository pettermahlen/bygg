#!/bin/sh

java -classpath ~/.m2/repository/com/pettermahlen/bygg/bygg/1.0-SNAPSHOT/bygg-1.0-SNAPSHOT.jar:/Users/pettermahlen/.m2/repository/com/google/guava/guava/r08/guava-r08.jar:/Users/pettermahlen/.m2/repository/net/sf/jopt-simple/jopt-simple/3.2/jopt-simple-3.2.jar:/Users/pettermahlen/.m2/repository/org/codehaus/janino/commons-compiler-jdk/2.6.1/commons-compiler-jdk-2.6.1.jar:/Users/pettermahlen/.m2/repository/org/codehaus/janino/commons-compiler/2.6.1/commons-compiler-2.6.1.jar com.pettermahlen.bygg.Bygg $*

#!/usr/bin/bash

# scp ./target/TaskManager-1.0-SNAPSHOT.war www@tune-it.dev.elcom.spb.ru:~/liferay/tune-it/tmp
scp -P 2222 ./target/TaskManager-1.0-SNAPSHOT.war s191998@helios.cs.ifmo.ru:~

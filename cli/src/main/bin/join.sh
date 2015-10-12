#!/bin/sh

cat $1/src/main/bin/stub.sh $1/target/cli-$2.jar > $1/target/sprint0 && chmod +x target/sprint0

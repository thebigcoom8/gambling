#!/bin/sh
cd "$(dirname "$0")"
if [ -f gambling.jar.new ]; then
  mv -f gambling.jar.new gambling.jar
fi
exec java -jar gambling.jar "$@"

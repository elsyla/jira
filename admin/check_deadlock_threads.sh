#!/usr/bin/env bash
# check deadlock threads
# https://confluence.atlassian.com/jirakb/deadlock-detected-on-startup-error-in-logfile-627212328.html

FILE=/opt/atlassian/jira/logs/catalina.out

FOUND=`tail -1000 $FILE | grep 'deadlock has been detected'`

if [ -n "$FOUND" ]; then
  echo "DEADLOCK THREADS FOUND IN $FILE"
  echo $FOUND
  exit 1
fi

exit 0

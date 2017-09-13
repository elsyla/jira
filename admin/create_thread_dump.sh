#!/usr/bin/env bash
# create a thread dump 6 times within one minute as recommended by Atlassian
# https://confluence.atlassian.com/kb/generating-a-thread-dump-794369342.html

for i in {1..1}
do
  echo "create thread dump $i"
  kill -3 `pgrep -u jira`
  sleep 10
done

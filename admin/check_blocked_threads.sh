#!/usr/bin/env bash
# run jvmtop and parse output for blocked thread(s)

FILE=/var/tmp/jvmtop.output
SAVEFILE=$FILE.with.blocked.threads
FOUND=false

check_blocked_threads() {
  START=false
  while read LINE; do
    if [[ $LINE == *"TID   NAME"* ]]; then
      START=true
    elif [[ $LINE == *"Note: Only"* ]]; then
      START=false
    elif $START; then
      LINE2=`echo $LINE | tr -d '[:space:]'`
      if [[ "${LINE2: -1}" != "%" ]]; then
        echo "FOUND BLOCKED THREAD: $LINE"
        cp -f $FILE $SAVEFILE
        FOUND=true
      fi
    fi
  done < $FILE
}

# first try
su -l jira -c '/usr/bin/jvmtop/jvmtop.sh `pgrep java` -d 2 -n 21 > /var/tmp/jvmtop.output 2>&1'
check_blocked_threads

# second try with fewer jvmtop cycles if first failed
if $FOUND; then
  FOUND=false
  sleep 30
  su -l jira -c '/usr/bin/jvmtop/jvmtop.sh `pgrep java` -d 2 -n 11 > /var/tmp/jvmtop.output 2>&1'
  check_blocked_threads
fi

if $FOUND; then
  echo "BLOCKED THREADS FOUND IN $SAVEFILE"
  # generate a thread dump for troubleshooting
  kill -3 `pgrep -u jira`
  exit 1
else
  echo "NO BLOCKED THREAD FOUND"
fi

exit 0

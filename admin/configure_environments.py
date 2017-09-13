#!/usr/bin/env python
# iterate through data and make symbolic links for *<blue|green> on servers

import json
import socket
import os, re

ifile    = '/var/tmp/jira-service.inventory'
thishost = socket.gethostname()
paths    = [ '/opt/atlassian/jira', '/opt/atlassian/jira-data' ]
links    = {
             '/opt/atlassian/jira'                             : '/home/jira/jira',
             '/opt/atlassian/jira-data/scripts/xyz'             : '/opt/atlassian/jira/atlassian-jira/WEB-INF/classes/com/xyz' }


def main():
    with open(ifile) as file:
        inventory = json.load(file)

    try:
        state = inventory.get('_meta').get('hostvars').get(thishost).get('bg_state')
        if not re.match('^(blue|green)$', state):
                print('error: invalid state {}'.format(state))
                exit(1)
    except:
        print('error: {} is in unknown state'.format(thishost))
        exit(1)

    print('{} is in {} state'.format(thishost, state))

    # creating symlinks
    for path, link in links.items():
        if not os.path.lexists(link):
            print('creating symlink for {} from {}'.format(path, link))
            rc = os.symlink(path, link)

    # creating main paths
    for path in paths:
        if not os.path.exists(path):
            print('creating new {}'.format(path))
            rc = os.system('mkdir {} && chown jira:jira {}'.format(path, path))
            print('result = {}'.format(rc))

    # iterate through files and create symlinks
    for path in paths:
        for root, directories, filenames in os.walk(path):
            for filename in filenames:
                file = os.path.join(root, filename) 
                if re.search(".+\.{}$".format(state), file):
                    shortfile = re.sub('\.' + state, '', file)
                    print('removing any previous symlink {}'.format(shortfile))
                    rc = os.system('rm -f {}'.format(shortfile))
                    print('result = {}'.format(rc))
                    print('creating symlink {}'.format(shortfile))
                    rc = os.system('ln -sf {} {}'.format(file, shortfile))
                    print('result = {}'.format(rc))
                    rc = os.system('chown -h jira:jira {}'.format(shortfile))
                    print('result = {}'.format(rc))


if __name__ == "__main__":
    main()
    exit(0)

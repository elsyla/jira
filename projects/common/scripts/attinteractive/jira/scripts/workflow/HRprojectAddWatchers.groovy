package com.onresolve.jira.groovy.canned.workflow.postfunctions

import com.atlassian.jira.ComponentManager

def componentManager = ComponentManager.getInstance()
def watcherManager = componentManager.getWatcherManager()
def userManager = componentManager.getUserUtil()

def watchUsers = {usernames ->
   usernames.each {
         def user = userManager.getUser(it)
         watcherManager.startWatching(user, issue.getGenericValue())
      }
}

   def users = ["hrnewforms"]
   watchUsers(users)

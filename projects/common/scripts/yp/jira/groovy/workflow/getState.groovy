userPropertyManager = com.atlassian.jira.component.ComponentAccessor.getUserPropertyManager()
crowdService = com.atlassian.jira.component.ComponentAccessor.getCrowdService()
userManager = com.atlassian.jira.component.ComponentAccessor.getUserManager()
reporter = issue.getReporterUser().getName()
user = crowdService.getUser(reporter)
location = userPropertyManager.getPropertySet(user).getString("jira.meta.officeCode")
issue.setCustomFieldValue(location,

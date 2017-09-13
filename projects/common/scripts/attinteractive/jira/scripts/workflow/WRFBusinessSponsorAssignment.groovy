import com.atlassian.jira.bc.project.component.ProjectComponent
import com.atlassian.jira.bc.project.component.ProjectComponentManager
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.fields.CustomField

MutableIssue issue = issue
CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager()
CustomField businessSponsorField = customFieldManager.getCustomFieldObjectByName("Business Sponsor")
def businessSponsor = (String) issue.getCustomFieldValue(businessSponsorField)
ProjectComponentManager projectComponentManager = ComponentAccessor.getProjectComponentManager()
ProjectComponent component = projectComponentManager.findByComponentName(11881L, businessSponsor)
def lead = component.getLead()
issue.setAssigneeId(lead)
issue.store()
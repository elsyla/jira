import com.atlassian.jira.issue.Issue
import com.atlassian.jira.ComponentManager
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField
import org.apache.log4j.Category
import com.opensymphony.workflow.InvalidInputException

CustomFieldManager customFieldManager = ComponentManager.getInstance().getCustomFieldManager()
Issue myIssue = issue
def su = customFieldManager.getCustomFieldObjectByName("Separating username")
def suv = myIssue.getCustomFieldValue(su)
def nku = customFieldManager.getCustomFieldObjectByName("No Known username")
if (!suv && myIssue.getCustomFieldValue(nku)){
return false
}
else if (suv) {
return true
}
else if (!suv && !myIssue.getCustomFieldValue(nku)){
invalidInputException = new InvalidInputException("Must enter either a 'Separating username' OR put a checkmark in the 'No Known username' field.")
}

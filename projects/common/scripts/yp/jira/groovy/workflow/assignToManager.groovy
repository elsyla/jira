import groovy.util.logging.Log4j;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.ApplicationUsers;

def reporter = issue.getReporterUser().getName();
def user = ComponentAccessor.getCrowdService().getUser(reporter);

def appuser = ApplicationUsers.from(user);
def manager = ComponentAccessor.getUserPropertyManager().getPropertySet(appuser).getString("jira.meta.manager");
def managerUser = ComponentAccessor.getUserManager().getUserByName(manager);

// set the assignee with reporter's manager
issue.setAssignee(managerUser);

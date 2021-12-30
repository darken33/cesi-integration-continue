import hudson.plugins.git.*;

String jobName = new File('/var/jenkins_home/.ssh/job_name.txt').text.trim()
String jobRepoUrl = new File('/var/jenkins_home/.ssh/job_repo_url.txt').text.trim()

List<UserRemoteConfig> repoList = new ArrayList<>();
repoList.add(new UserRemoteConfig(jobRepoUrl, null, null, "jenkins"));

def scm = new GitSCM(repoList,
    Collections.singletonList(new BranchSpec("")),
                false, new ArrayList<>(),
                null, null, new ArrayList<>())
scm.branches = [new BranchSpec("*/master")];

def flowDefinition = new org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition(scm, "Jenkinsfile")

def parent = Jenkins.instance
def job = new org.jenkinsci.plugins.workflow.job.WorkflowJob(parent, jobName)
job.definition = flowDefinition

credentials_store = parent.getExtensionList(
  'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
  )[0].getStore()


parent.reload()

import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import hudson.plugins.sshslaves.*
import org.jenkinsci.plugins.plaincredentials.impl.*

String token = new File('/var/jenkins_home/.ssh/gitlab_token.txt').text
global_domain = Domain.global()
credentials_store =
Jenkins.instance.getExtensionList(
  'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
  )[0].getStore()
credentials = new StringCredentialsImpl(
    CredentialsScope.GLOBAL,
    "gitlab",
    "gitlab",
    new hudson.util.Secret(token)
)
credentials_store.addCredentials(global_domain, credentials)

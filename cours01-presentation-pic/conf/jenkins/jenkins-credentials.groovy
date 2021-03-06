import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import hudson.plugins.sshslaves.*

String key = new File('/var/jenkins_home/.ssh/id_rsa').text
global_domain = Domain.global()
credentials_store =
Jenkins.instance.getExtensionList(
  'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
  )[0].getStore()
credentials = new BasicSSHUserPrivateKey(
    CredentialsScope.GLOBAL,
    "jenkins",
    "jenkins",
    new BasicSSHUserPrivateKey.DirectEntryPrivateKeySource(key),
    "",
    ""
)
credentials_store.addCredentials(global_domain, credentials)

#!groovy

import java.lang.System
import hudson.model.*
import jenkins.model.*
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import hudson.plugins.sshslaves.verifiers.NonVerifyingKeyVerificationStrategy
import hudson.model.Node.*

String ip = new File('/var/jenkins_home/.ssh/slave_ip.txt').text
String path = new File('/var/jenkins_home/.ssh/slave_path.txt').text

// Define a "Permanent Agent"
ComputerLauncher launcher = new SSHLauncher(ip,                 // The host to connect to
                                   22,      // The port to connect on
                                   "slave",        // The credentials id to connect as
                                   "",  // Options passed to the java vm
                                   "",            // Path to the host jdk installation
                                   "",  // This will prefix the start slave command
                                   "", // This will suffix the start slave command
                                   null, // Launch timeout in seconds
                                   null,       // The number of times to retry connection if the SSH connection is refused
                                   null,       // The number of seconds to wait between retries
                                   new NonVerifyingKeyVerificationStrategy())
Slave agent = new DumbSlave(
        "slave",
        path,
        launcher)
agent.nodeDescription = "slave"
agent.numExecutors = 2
agent.labelString = "slave"
agent.mode = Node.Mode.NORMAL
agent.retentionStrategy = new RetentionStrategy.Always()

// Create a "Permanent Agent"
Jenkins.instance.addNode(agent)

// launcher
// computer = agent.toComputer();
// computer.connect(false).get();

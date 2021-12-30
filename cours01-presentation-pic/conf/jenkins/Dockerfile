ARG JENKINS_IMAGE
FROM $JENKINS_IMAGE

ARG JENKINS_PWD

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ENV JENKINS_PWD=$JENKINS_PWD

COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

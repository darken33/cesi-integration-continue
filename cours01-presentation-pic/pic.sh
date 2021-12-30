#!/bin/bash

env() {
  # fail if anything errors
  set -e
  # fail if a call is missing an argument
  set -u

  source pic.env

  # Constantsue
  # vm params
  PIC_NAME="pic"
  SLAVE_NAME="pic"
  SLAVE_FOLDER="${PIC_NAME}/${SLAVE_NAME}"
  # jenkins params
  JENKINS_NAME="jenkins"
  JENKINS_HOME="${PIC_NAME}/jenkins_home"
  JENKINS_DATA="$JENKINS_NAME-data"
  # gitlab params
  GITLAB_NAME="gitlab"
  GITLAB_DATA="$GITLAB_NAME-data"
  GITLAB_CONF="$GITLAB_NAME-conf"
  GITLAB_LOG="$GITLAB_NAME-log"
  # nexus params
  NEXUS_NAME="nexus"
  NEXUS_DATA="$NEXUS_NAME-data"
  # sonar params
  SONAR_NAME="sonar"
  SONAR_DATA="$SONAR_NAME-data"
  # Wildfly
  JBOSS_WILDFLY_NAME="wildfly"

  # workspaces
  WS=$(pwd | sed s/home/hosthome/)
  WSLOCAL=$(pwd)

  SLAVE_IP=$(docker-machine ip $SLAVE_NAME || true)
  PIC_IP=$(docker-machine ip $PIC_NAME || true)
}

# Functions
usage()
{
  echo "usage: $0 <option>
  create
  start
  init
  stop
  drop
  status"
}

statusPic() {
  docker-machine ls

  # Messages
  echo "**************"
  echo "PIC IP : $PIC_IP"
  echo "  - Jenkins on http://$PIC_IP:$JENKINS_PORT"
  echo "  - Gitlab on http://$PIC_IP:$GITLAB_HTTP_PORT"
  echo "  - Nexus on http://$PIC_IP:$NEXUS_PORT"
  echo "  - SonarQube on http://$PIC_IP:$SONAR_PORT"
  echo "SLAVE IP : $SLAVE_IP"
  echo "  - Wildfly on http://$SLAVE_IP:$JBOSS_WILDFLY_PORT"
  echo "**************"
}


# drop VM pic & slave
dropPic() {
  docker-machine rm -f -y $PIC_NAME || true
#  docker-machine rm -f -y $SLAVE_NAME || true

  rm -rf ${PIC_NAME} || true
}

softStopPicService() {
  docker-machine ssh $PIC_NAME -- docker stop $JENKINS_NAME
  docker-machine ssh $PIC_NAME -- docker stop $GITLAB_NAME
  docker-machine ssh $PIC_NAME -- docker rm $GITLAB_NAME
  docker-machine ssh $PIC_NAME -- docker stop $NEXUS_NAME
  docker-machine ssh $PIC_NAME -- docker stop $SONAR_NAME
}

stopPicService() {
  docker-machine ssh $PIC_NAME -- docker stop $JENKINS_NAME
  docker-machine ssh $PIC_NAME -- docker rm $JENKINS_NAME
  docker-machine ssh $PIC_NAME -- docker stop $GITLAB_NAME
  docker-machine ssh $PIC_NAME -- docker rm $GITLAB_NAME
  docker-machine ssh $PIC_NAME -- docker stop $NEXUS_NAME
  docker-machine ssh $PIC_NAME -- docker rm $NEXUS_NAME
  docker-machine ssh $PIC_NAME -- docker stop $SONAR_NAME
  docker-machine ssh $PIC_NAME -- docker rm $SONAR_NAME
}

restartPicService() {
  docker-machine ssh $PIC_NAME -- docker start $JENKINS_NAME
  # docker run Gitlab
  CMD="docker run -d --name $GITLAB_NAME  -p $GITLAB_HTTP_PORT:80 -p $GITLAB_SSH_PORT:22"
  CMD="$CMD -v $GITLAB_CONF:/etc/gitlab"
  CMD="$CMD -v $GITLAB_LOG:/var/log/gitlab"
  CMD="$CMD -v $GITLAB_DATA:/var/opt/gitlab"
  CMD="$CMD -e GITLAB_OMNIBUS_CONFIG=\"root_pass='$GITLAB_PWD'; gitlab_rails['initial_root_password']=root_pass; manage_accounts['enable'] = true;\""
  CMD="$CMD $GITLAB_IMAGE"
  docker-machine ssh $PIC_NAME -- $CMD
  docker-machine ssh $PIC_NAME -- docker start $NEXUS_NAME
  docker-machine ssh $PIC_NAME -- docker start $SONAR_NAME
}

startPicService() {
  SLAVE_IP=$(docker-machine ip $SLAVE_NAME || true)
  PIC_IP=$(docker-machine ip $PIC_NAME || true)
  # docker run Jenkins
  docker-machine ssh $PIC_NAME -- docker build -t my$JENKINS_IMAGE \
    --build-arg JENKINS_IMAGE=$JENKINS_IMAGE \
    --build-arg JENKINS_PWD=$JENKINS_PWD \
    $WS/conf/jenkins/. \

  docker-machine ssh $PIC_NAME -- docker run -d --name $JENKINS_NAME -p $JENKINS_PORT:8080 \
    -v $JENKINS_DATA:/var/jenkins_home \
    -v $WS/$JENKINS_HOME/.ssh/:/var/jenkins_home/.ssh \
    my$JENKINS_IMAGE

  # docker run Gitlab
  CMD="docker run -d --name $GITLAB_NAME  -p $GITLAB_HTTP_PORT:80 -p $GITLAB_SSH_PORT:22"
  CMD="$CMD -v $GITLAB_CONF:/etc/gitlab"
  CMD="$CMD -v $GITLAB_LOG:/var/log/gitlab"
  CMD="$CMD -v $GITLAB_DATA:/var/opt/gitlab"
  CMD="$CMD -e GITLAB_OMNIBUS_CONFIG=\"root_pass='$GITLAB_PWD'; gitlab_rails['initial_root_password']=root_pass; manage_accounts['enable'] = true;\""
  CMD="$CMD $GITLAB_IMAGE"
  docker-machine ssh $PIC_NAME -- $CMD

  # docker run Nexus
  docker-machine ssh $PIC_NAME -- docker run -d --name $NEXUS_NAME -p $NEXUS_PORT:8081 \
    -v $NEXUS_DATA:/nexus-data $NEXUS_IMAGE

  # docker run SonarQube
  docker-machine ssh $PIC_NAME -- docker run -d --name $SONAR_NAME -p $SONAR_PORT:9000 \
    -v $SONAR_DATA:/opt/sonarqube $SONAR_IMAGE

  # ssh config on jenkins
  rm -f $JENKINS_HOME/.ssh/known_hosts
  docker-machine ssh $PIC_NAME -- "docker exec -i $JENKINS_NAME bash -c \"ssh-keyscan $SLAVE_IP >> ~/.ssh/known_hosts\""
#  docker-machine ssh $PIC_NAME -- "docker exec -i $JENKINS_NAME bash -c \"ssh-keyscan 192.168.99.105 >> ~/.ssh/known_hosts\""
  # echo -e "Host $SLAVE_IP\n\tHostname $SLAVE_IP\n\tPort 22\n\tIdentityFile ~/.ssh/id_rsa\n\tUser docker" > $WSLOCAL/$JENKINS_HOME/.ssh/config

  # git/ssh config on slave
  docker-machine ssh $SLAVE_NAME -- 'cat '$WS'/'$JENKINS_HOME'/.ssh/id_rsa.pub >> .ssh/authorized_keys'
  docker-machine ssh $SLAVE_NAME -- "echo -e \"Host $PIC_IP\n\tHostname $PIC_IP\n\tPort $GITLAB_SSH_PORT\n\tIdentityFile $WS/$JENKINS_HOME/.ssh/id_rsa\n\tUser git\" > ~/.ssh/config"
  docker-machine ssh $SLAVE_NAME -- git config --global user.email jenkins@pic
  docker-machine ssh $SLAVE_NAME -- git config --global user.name jenkins
#  docker-machine ssh $SLAVE_NAME -- "ssh-keyscan -p 2222 192.168.99.104 >> ~/.ssh/known_hosts"
  docker-machine ssh $SLAVE_NAME -- "ssh-keyscan -p $GITLAB_SSH_PORT $PIC_IP >> ~/.ssh/known_hosts"
}


initConf() {
  # Gitlab generate access token
  token=ZA8bsXbw9xxxGccmqgzb
  #$(docker-machine ssh $SLAVE_NAME -- docker run --rm -i -w /usr/src/app \
  #  -v $WS/conf/gitlab:/usr/src/app my$PYTHON_IMAGE \
  #    personal_access_token.py mytoken http://$PIC_IP:$GITLAB_HTTP_PORT root $GITLAB_PWD)
  #sleep 2

  # urlencode id_rsa.pub
  key=AAAAB3NzaC1yc2EAAAADAQABAAACAQDQto2yCDHSipdx9tKefoy0zmtwmjoEGrEgHs/bPmOXGK8kYjLfo89ZhglDxGyTgo90nXvOwTn4yCg15TGWt1iCkqo0fmExkBfJfWqmGr1s6nMk1fPDeD+ZrHGtftudB8J6a8UKYOs5M7HbMa06WYhFd/dfiqG8v9xO5nOlblEgpwOqhhHhkmn/JOcBiqrAqXeAomxpamyZjYgM/p/C57NQPXgDvPoTR7BvmXDFxhbWzNfRb5KUM7L/x5aNr9rflSDOAP7NV9knDFVQewM+DGcO7NFKgO9hbmo6r5DqmvzES7QNU9ltd1D244dXzoiBgJqZFxBlZzQw7oMIp6Gd77ZzKtMPyDeTa+c24viNfK9B2vu1IQ45EMCbTLOG83Os/djOsO00t6ciztHYN5rtLgQvu3ExyhQN67ekRXKy/cJNkwsHIuDv189wNONJOgNaeAY/bR8cES9U8lMMmxyFaYIzoq9GlcLPiQDgX3nC+cZ7BBoCxCuJnMGusqgjNUN4xiEM7EKFYOUMKWOZIAN9zU92mJTGdMI8UOIPqqv7dKkH8+zytLzVVwxUO3LWgH61i5FuXUx/t9AyhWncAsnp6araWIGhpuUdQqV3ANbSTX6PaztVKZIaJIqqkBBXrhi5FqkbcDRRWiQLSPrWBWl8zwHB9kkl9xgN8qT21yqa8J3nzw==
#  key_urlencode=$(docker-machine ssh $SLAVE_NAME -- docker run --rm -i -w /usr/src/app \
#    -v $WS/conf/python:/usr/src/app my$PYTHON_IMAGE urlencode.py "'"$key"'")

  # add ssh key on gitlab
#  curl -d "key=$key_urlencode&title=jenkins@pic" \
#    -H "Content-Type: application/x-www-form-urlencoded" -H "PRIVATE-TOKEN: $token" \
#    -X POST http://$PIC_IP:$GITLAB_HTTP_PORT/api/v4/user/keys

  # Jenkins script doc : https://wiki.jenkins.io/display/JENKINS/Jenkins+Script+Console
  # http://nithril.github.io/ci/2015/04/22/jenkins-job-dsl-pipeline-part1/
  # create jenkins credentials for slave
  #curl -u "admin:$JENKINS_PWD" -d "script=$(< ./conf/jenkins/test.groovy)" http://$PIC_IP:$JENKINS_PORT/scriptText
  docker-machine ssh $SLAVE_NAME -- docker run --rm -i -w /usr/src/app \
    -v $WS/conf/jenkins:/usr/src/app my$PYTHON_IMAGE \
      upload.py slave-credentials.groovy http://$PIC_IP:$JENKINS_PORT admin $JENKINS_PWD

  # create jenkins credentials for gitlab
  docker-machine ssh $SLAVE_NAME -- docker run --rm -i -w /usr/src/app \
        -v $WS/conf/jenkins:/usr/src/app my$PYTHON_IMAGE \
          upload.py jenkins-credentials.groovy http://$PIC_IP:$JENKINS_PORT admin $JENKINS_PWD

  # create jenkins credentials for gitlab secret token
  echo $token > $WSLOCAL/$JENKINS_HOME/.ssh/gitlab_token.txt
  docker-machine ssh $SLAVE_NAME -- docker run --rm -i -w /usr/src/app \
        -v $WS/conf/jenkins:/usr/src/app my$PYTHON_IMAGE \
          upload.py gitlab-credentials.groovy http://$PIC_IP:$JENKINS_PORT admin $JENKINS_PWD
  rm $WSLOCAL/$JENKINS_HOME/.ssh/gitlab_token.txt

  # create node with credentials
  echo $SLAVE_IP > $WSLOCAL/$JENKINS_HOME/.ssh/slave_ip.txt
  echo "$WS/$SLAVE_FOLDER" > $WSLOCAL/$JENKINS_HOME/.ssh/slave_path.txt
  docker-machine ssh $SLAVE_NAME -- docker run --rm -i -w /usr/src/app \
        -v $WS/conf/jenkins:/usr/src/app my$PYTHON_IMAGE \
          upload.py slave.groovy http://$PIC_IP:$JENKINS_PORT admin $JENKINS_PWD
  rm -f $WSLOCAL/$JENKINS_HOME/.ssh/slave_*.txt

  # repo & job project-generator
  project_name="project-generator"
  curl -H "PRIVATE-TOKEN: $token" -X POST "http://$PIC_IP:$GITLAB_HTTP_PORT/api/v4/projects?name=$project_name"
  sleep 2
  # https://stackoverflow.com/questions/16963309/how-create-and-configure-a-new-jenkins-job-using-groovy
  # https://github.com/linagora/james-jenkins/blob/master/create-dsl-job.groovy
  echo $project_name > $WSLOCAL/$JENKINS_HOME/.ssh/job_name.txt
  echo "ssh://git@$PIC_IP:$GITLAB_SSH_PORT/root/$project_name.git" > $WSLOCAL/$JENKINS_HOME/.ssh/job_repo_url.txt
  docker-machine ssh $SLAVE_NAME -- docker run --rm -i -w /usr/src/app \
        -v $WS/conf/jenkins:/usr/src/app my$PYTHON_IMAGE \
          upload.py job-creation.groovy http://$PIC_IP:$JENKINS_PORT admin $JENKINS_PWD
  docker-machine ssh $SLAVE_NAME -- rm -rf $project_name
  docker-machine ssh $SLAVE_NAME -- git clone ssh://git@$PIC_IP:$GITLAB_SSH_PORT/root/$project_name.git
  docker-machine ssh $SLAVE_NAME -- cp $WS/conf/$project_name/* $project_name/
  docker-machine ssh $SLAVE_NAME -- sed -i 's/\$PYTHON_IMAGE/'$PYTHON_IMAGE'/g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- sed -i 's/\$PIC_IP/'$PIC_IP'/g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- sed -i 's/\$JENKINS_PORT/'$JENKINS_PORT'/g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- sed -i 's/\$GITLAB_HTTP_PORT/'$GITLAB_HTTP_PORT'/g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- sed -i 's/\$GITLAB_SSH_PORT/'$GITLAB_SSH_PORT'/g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- sed -i 's/\$JENKINS_PWD/'$JENKINS_PWD'/g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$JBOSS_FORGE_IMAGE,'$JBOSS_FORGE_IMAGE',g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- cp $WS/conf/jenkins/job-creation.groovy $project_name/
  docker-machine ssh $SLAVE_NAME -- cp $WS/conf/jenkins/upload.py $project_name/
  docker-machine ssh $SLAVE_NAME -- cp $WS/conf/project-template/Jenkinsfile $project_name/Jenkinsfile-build
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$MAVEN_IMAGE,'$MAVEN_IMAGE',g' $project_name/Jenkinsfile-build
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$JBOSS_WILDFLY_IMAGE,'$JBOSS_WILDFLY_IMAGE',g' $project_name/Jenkinsfile-build
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$JBOSS_WILDFLY_PORT,'$JBOSS_WILDFLY_PORT',g' $project_name/Jenkinsfile-build
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$SONAR_SCANNER_IMAGE,'$SONAR_SCANNER_IMAGE',g' $project_name/Jenkinsfile-build
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$SONAR_PORT,'$SONAR_PORT',g' $project_name/Jenkinsfile-build
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$PIC_IP,'$PIC_IP',g' $project_name/Jenkinsfile-build
  docker-machine ssh $SLAVE_NAME -- cp $WS/conf/nexus/settings.xml $project_name/
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$PIC_IP,'$PIC_IP',g' $project_name/settings.xml
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$NEXUS_PWD,'$NEXUS_PWD',g' $project_name/settings.xml
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$NEXUS_PORT,'$NEXUS_PORT',g' $project_name/settings.xml
  docker-machine ssh $SLAVE_NAME -- cp $WS/conf/nexus/distrib-mgt.xml $project_name/
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$PIC_IP,'$PIC_IP',g' $project_name/distrib-mgt.xml
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$NEXUS_PORT,'$NEXUS_PORT',g' $project_name/distrib-mgt.xml
  docker-machine ssh $SLAVE_NAME -- cp $WS/conf/sonar/sonar-project.properties $project_name/
  docker-machine ssh $SLAVE_NAME -- "cd $project_name && git add . && git commit -m \"init\" && git push"
  rm -f $WSLOCAL/$JENKINS_HOME/.ssh/job_*.txt

  # repo & job presentation
  project_name="presentation"
  curl -H "PRIVATE-TOKEN: $token" -X POST http://$PIC_IP:$GITLAB_HTTP_PORT/api/v4/projects?name=$project_name
  sleep 2
  echo $project_name > $WSLOCAL/$JENKINS_HOME/.ssh/job_name.txt
  echo "ssh://git@$PIC_IP:$GITLAB_SSH_PORT/root/$project_name.git" > $WSLOCAL/$JENKINS_HOME/.ssh/job_repo_url.txt
  docker-machine ssh $SLAVE_NAME -- docker run --rm -i -w /usr/src/app \
        -v $WS/conf/jenkins:/usr/src/app my$PYTHON_IMAGE \
          upload.py job-creation.groovy http://$PIC_IP:$JENKINS_PORT admin $JENKINS_PWD
  docker-machine ssh $SLAVE_NAME -- rm -rf $project_name
  docker-machine ssh $SLAVE_NAME -- git clone ssh://git@$PIC_IP:$GITLAB_SSH_PORT/root/$project_name.git
  docker-machine ssh $SLAVE_NAME -- cp $WS/*.adoc $project_name/
  docker-machine ssh $SLAVE_NAME -- cp -r $WS/diagrams $project_name/
  docker-machine ssh $SLAVE_NAME -- cp -r $WS/drawio $project_name/
  docker-machine ssh $SLAVE_NAME -- cp -r $WS/images $project_name/
  docker-machine ssh $SLAVE_NAME -- cp $WS/conf/$project_name/Jenkinsfile $project_name/
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$ASCIIDOCTOR_IMAGE,'$ASCIIDOCTOR_IMAGE',g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$PDF_IMAGE,'$PDF_IMAGE',g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$NEXUS_PWD,'$NEXUS_PWD',g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$NEXUS_PORT,'$NEXUS_PORT',g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- sed -i 's,\$PIC_IP,'$PIC_IP',g' $project_name/Jenkinsfile
  docker-machine ssh $SLAVE_NAME -- "cd $project_name && git add . && git commit -m \"init\" && git push"
  rm -f $WSLOCAL/$JENKINS_HOME/.ssh/job_*.txt

  # Nexus - create raw hosted repo
  curl -u admin:$NEXUS_PWD -X POST \
        -H "Content-Type: application/json" -H "accept: application/json" \
        http://$PIC_IP:$NEXUS_PORT/service/rest/v1/script \
        -d @$WSLOCAL/conf/nexus/create-raw-repo.json
  curl -u admin:$NEXUS_PWD -X POST \
        http://$PIC_IP:$NEXUS_PORT/service/rest/v1/script/createRawRepo/run \
        -H "accept: application/json" -H "Content-Type: text/plain"
}

createPic() {
  # create VM pic & slave
  docker-machine create --driver virtualbox --virtualbox-memory $PIC_RAM --virtualbox-cpu-count $PIC_CPU $PIC_NAME
#  docker-machine create --driver virtualbox --virtualbox-memory $SLAVE_RAM --virtualbox-cpu-count $SLAVE_CPU $SLAVE_NAME

  # ports forwarding to host
  vboxmanage controlvm $PIC_NAME natpf1 "$JENKINS_NAME,tcp,,$JENKINS_PORT,,$JENKINS_PORT"
  vboxmanage controlvm $PIC_NAME natpf1 "$GITLAB_NAME,tcp,,$GITLAB_HTTP_PORT,,$GITLAB_HTTP_PORT"
  vboxmanage controlvm $PIC_NAME natpf1 "${GITLAB_NAME}_ssh,tcp,,$GITLAB_SSH_PORT,,$GITLAB_SSH_PORT"
  vboxmanage controlvm $PIC_NAME natpf1 "$NEXUS_NAME,tcp,,$NEXUS_PORT,,$NEXUS_PORT"
  vboxmanage controlvm $PIC_NAME natpf1 "$SONAR_NAME,tcp,,$SONAR_PORT,,$SONAR_PORT"
  vboxmanage controlvm $SLAVE_NAME natpf1 "$JBOSS_WILDFLY_NAME,tcp,,$JBOSS_WILDFLY_PORT,,$JBOSS_WILDFLY_PORT"

  # conf docker registry
  docker-machine ssh $PIC_NAME -- sudo cp $WS/conf/docker/docker-daemon.json /etc/docker/daemon.json
#  docker-machine ssh $SLAVE_NAME -- sudo cp $WS/conf/docker/docker-daemon.json /etc/docker/daemon.json

  # restart docker service
  docker-machine ssh $PIC_NAME -- sudo /etc/init.d/docker restart
#  docker-machine ssh $SLAVE_NAME -- sudo /etc/init.d/docker restart

  # Generate ssh keys on pic
  mkdir -p $JENKINS_HOME/.ssh
  rm -f $JENKINS_HOME/.ssh/id_rsa*
  ssh-keygen -t rsa -b 4096 -C 'jenkins@pic' -f $JENKINS_HOME/.ssh/id_rsa -q -P ""

  # docker data
  docker-machine ssh $PIC_NAME -- docker volume create $JENKINS_DATA
  docker-machine ssh $PIC_NAME -- docker volume create $GITLAB_DATA
  docker-machine ssh $PIC_NAME -- docker volume create $GITLAB_CONF
  docker-machine ssh $PIC_NAME -- docker volume create $GITLAB_LOG
  docker-machine ssh $PIC_NAME -- docker volume create $NEXUS_DATA
  docker-machine ssh $PIC_NAME -- docker volume create $SONAR_DATA

  # java on slave
  mkdir -p $SLAVE_FOLDER
  curl -L -C - -b "oraclelicense=accept-securebackup-cookie" -o $SLAVE_FOLDER/jdk.tar.gz $JDK_URL
  tar -zxvf $SLAVE_FOLDER/jdk.tar.gz -C $SLAVE_FOLDER
  mv $SLAVE_FOLDER/jdk*/ $SLAVE_FOLDER/jdk
  rm $SLAVE_FOLDER/jdk.tar.gz

  # Python on slave
  docker-machine ssh $SLAVE_NAME -- docker build -t my${PYTHON_IMAGE} --build-arg PYTHON_IMAGE=$PYTHON_IMAGE $WS/conf/python/.

  # pull images on PIC
  docker-machine ssh $PIC_NAME -- docker pull $JENKINS_IMAGE
  docker-machine ssh $PIC_NAME -- docker pull $GITLAB_IMAGE
  docker-machine ssh $PIC_NAME -- docker pull $NEXUS_IMAGE
  docker-machine ssh $PIC_NAME -- docker pull $SONAR_IMAGE

  # pull images on slave
  docker-machine ssh $SLAVE_NAME -- docker pull $JBOSS_FORGE_IMAGE
  docker-machine ssh $SLAVE_NAME -- docker pull $JBOSS_WILDFLY_IMAGE
  docker-machine ssh $SLAVE_NAME -- docker pull $ASCIIDOCTOR_IMAGE
  docker-machine ssh $SLAVE_NAME -- docker pull $PDF_IMAGE
  docker-machine ssh $SLAVE_NAME -- docker pull $SONAR_SCANNER_IMAGE

}

stopPic()
{
  softStopPicService
  docker-machine stop $PIC_NAME || true
#  docker-machine stop $SLAVE_NAME || true
}

startPic()
{
  docker-machine start $PIC_NAME || true
#  docker-machine start $SLAVE_NAME || true
  restartPicService
  statusPic
}

main() {
  start=$(date +%s)
  echo "$1 ..."
  case $1 in
    create)
    createPic
    ;;
    start)
    startPic
    ;;
    init)
    initConf
    ;;
    stop)
    stopPic
    ;;
    drop)
    dropPic
    ;;
    status)
    statusPic
    ;;
    *)
    usage
    ;;
  esac
  stop=$(date +%s)
  duration=$((stop - start))
  duration_formated=$(date +%M:%S  -ud @$duration)
  echo "$1 OK in $duration_formated"
}

# code
if [[ $# -ne 1 ]] ; then
  usage
else
  env
  main $1
fi
exit 0

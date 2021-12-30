#!groovy

import jenkins.model.*
import hudson.security.*
import jenkins.security.s2m.AdminWhitelistRule

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)

def password = System.getenv("JENKINS_PWD")
hudsonRealm.createAccount("admin", password)

instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()

instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)

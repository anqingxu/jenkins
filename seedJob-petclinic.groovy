#!/usr/bin/env groovy

pipelineJob('build-petclinic-using-jenkins-operator') {
    displayName('Build PetClinic using jenkins-operator')

    properties {
        pipelineTriggers {
            triggers {
                pollSCM 'H/2 * * * *'
            }
        }
    }

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/anqingxu/spring-petclinic.git')
                    }
                    branches('*/main')
                }
            }
            scriptPath('Jenkinsfile')
        }
    }
}

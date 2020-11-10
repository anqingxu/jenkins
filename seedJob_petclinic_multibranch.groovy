multibranchPipelineJob('spring-petclinic-ci') {
    branchSources {
        git {
            id('123456789') // IMPORTANT: use a constant and unique identifier
            remote('https://github.com/anqingxu/spring-petclinic.git')
            credentialsId('github-id')
            //includes('JENKINS-*')
            includes("master main release/* feature/* bugfix/*")
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(20)
        }
    }
}

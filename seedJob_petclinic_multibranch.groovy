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

    // check every minute for scm changes as well as new / deleted branches
    //triggers {
      //periodic(1)
    //}

    orphanedItemStrategy {
        discardOldItems {
            numToKeep(20)
        }
    }

    configure {
      it / 'triggers' << 'com.cloudbees.hudson.plugins.folder.computed.PeriodicFolderTrigger'{
        spec '* * * * *'
        interval "60000"
      }
    }
}

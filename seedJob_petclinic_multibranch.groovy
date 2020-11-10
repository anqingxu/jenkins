//See https://gist.github.com/tknerr/c79a514db4bdbfb4956aaf0ee53836c8#file-ci_jobs-groovy
// define the Git project + repos we want to build
def git_project = 'anqingxu'
def git_repos = ['spring-petclinic']

// create a pipeline job for each of the repos and for each feature branch.
for (git_repo in git_repos)
{
  multibranchPipelineJob("${git_repo}-ci") {
    branchSources {
      git {
          id(${git_project} + "_" + ${git_repo}) // IMPORTANT: use a constant and unique identifier
          remote('https://github.com/${git_project}/${git_repo}.git')
          credentialsId('github_id');
          includes('Jenkinsfile')
      }
    }

    // check every minute for scm changes as well as new / deleted branches
      triggers {
        periodic(1)
      }

    orphanedItemStrategy {
      discardOldItems {
          numToKeep(20)
      }
    }
  }
}

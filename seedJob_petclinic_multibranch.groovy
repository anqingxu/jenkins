//See https://gist.github.com/tknerr/c79a514db4bdbfb4956aaf0ee53836c8#file-ci_jobs-groovy
// define the Git project + repos we want to build
def git_project = 'anqingxu'
def git_repos = ['spring-petclinic']

// create a pipeline job for each of the repos and for each feature branch.
for (git_repo in git_repos)
{
  multibranchPipelineJob("${git_repo}-ci") {
    displayName "Build ${git_project}/${git_repo}"
    description "Multi-branch pipeline job for ${git_project}/${git_repo}"

    //See https://stackoverflow.com/questions/48284589/jenkins-jobdsl-multibranchpipelinejob-change-script-path
    //https://github.com/jenkinsci/job-dsl-plugin/wiki/Dynamic-DSL
    factory {
      workflowBranchProjectFactory {
        scriptPath('Jenkinsfile')
      }
    }

    // configure the branch / PR sources
    branchSources {
    /*
      github {
            // Set a unique ID as workaround of issue https://issues.jenkins-ci.org/browse/JENKINS-43693
            id(${git_project} + "_" + ${git_repo})
            scanCredentialsId('dockerhub_id')
            repoOwner(${git_project})
            repository(${git_repo})
            // // Build origin branches.
            // buildOriginBranch(true)
            // // Build origin branches also filed as PRs.
            // buildOriginBranchWithPR(true)
            // // Build origin PRs (merged with base branch).
            // buildOriginPRMerge(true)
            // // Build origin PRs (unmerged head).
            // buildOriginPRHead(false)
            // // Build fork PRs (merged with base branch).
            // buildForkPRMerge(true)
            // // Build fork PRs (unmerged head).
            // buildForkPRHead(false)
       }
      */
      git {
        id(${git_project} + "_" + ${git_repo}) // IMPORTANT: use a constant and unique identifier
        remote('https://github.com/${git_project}/${git_repo}.git')
        credentialsId('dockerhub_id');
        includes('Jenkinsfile')
      }
      /*
      branchSource {
        //https://github.com/cfpb/jenkins-automation/blob/main/src/main/groovy/jenkins/automation/builders/MultibranchPipelineJobBuilder.groovy
        //https://github.com/camptocamp/jenkins-initial-dsl-job/blob/master/generate_pipelines.groovy
        git {
          id(${git_project} + "_" + ${git_repo}) // IMPORTANT: use a constant and unique identifier
          remote('https://github.com/${git_project}/${git_repo}.git')
          credentialsId('dockerhub_id');
          includes('Jenkinsfile')
        }

        strategy {
          defaultBranchPropertyStrategy {
            props {
              // keep only the last 10 builds
              buildRetentionBranchProperty {
                buildDiscarder {
                  logRotator {
                    daysToKeepStr("-1")
                    numToKeepStr("10")
                    artifactDaysToKeepStr("-1")
                    artifactNumToKeepStr("-1")
                  }
                }
              }
            }
          }
        }
      }
      */
    }

    // check every minute for scm changes as well as new / deleted branches
    triggers {
      periodic(1)
    }

    // don't keep build jobs for deleted branches
    orphanedItemStrategy {
      discardOldItems {
        numToKeep(-1)
      }
    }
  }
}

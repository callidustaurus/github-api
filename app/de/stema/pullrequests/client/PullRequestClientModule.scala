package de.stema.pullrequests.client

import de.stema.pullrequests.dto.ProjectDTO
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}

import scala.concurrent.Future

/**
  * Binding a different implementations of [[PullRequestClient]] depending on the given configuration
  */
class PullRequestClientModule extends Module {
  def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      if(configuration.getBoolean("offline").getOrElse(false)) {
        bind[PullRequestClient].to[PullRequestSampleClient]
    } else {
        bind[PullRequestClient].to[PullRequestGitHubClient]
    })
  }
}

abstract class PullRequestClient {
  def getPullRequests(configuration: String): Seq[Future[ProjectDTO]]
}

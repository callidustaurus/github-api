package de.stema.pullrequests

import javax.inject.Inject

import de.stema.pullrequests.client.PullRequestClient
import play.api.mvc.{Action, AnyContent, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Endpoints for querying for pull-request information
  * @param pullRequestClient component that delivers necessary information about the pull-requests
  */
class PullRequestController @Inject()(pullRequestClient: PullRequestClient) extends Controller {

  /**
    * Delivers an overview-page with necessary pull-request information
    * @param configuration This configuration determines which organization should be read and which projects
    */
  def pullRequests(configuration: String): Action[AnyContent] = Action.async {
    val projects = pullRequestClient.getPullRequests(configuration)

    Future.sequence(projects).map { projects =>
      val totalNumberOfPRs = projects.map(_.pullRequests.size).sum
      Ok(de.stema.pullrequests.html.pullrequest(projects, totalNumberOfPRs))
    }
  }
}

package de.stema.pullrequests.client

import java.io.{File, PrintWriter}
import javax.inject.Inject

import com.typesafe.config.ConfigFactory
import de.stema.pullrequests.dto.{ProjectDTO, PullRequestDTO}
import de.stema.util.JsonObjectMapper
import play.api.Configuration
import play.api.http.Status._
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
  * Used to cal the github-api
  * @param wsClient the [[WSClient]] used to send request to github-api
  * @param jsonObjectMapper used to created necessary instances out of the response from github-api
  */
class PullRequestGitHubClient @Inject()(
                                    wsClient: WSClient,
                                    jsonObjectMapper: JsonObjectMapper
                                  ) extends PullRequestClient {

  def getPullRequests(configuration: String): Seq[Future[ProjectDTO]] = {
    val config = new Configuration(ConfigFactory.parseFile(new File(s"conf/$configuration.conf")))
    val organization = config.getString("organization").getOrElse("")
    val secret = config.getString("secret").getOrElse("")
    val projects: Seq[String] = config.getStringSeq("projects").getOrElse(Nil)

    projects.map { project =>
      wsClient
        .url(s"https://api.github.com/repos/$organization/$project/pulls")
        .withHeaders(("Authorization", secret))
        .get()
        .map { response =>
          response.status match {
            case OK =>
              new PrintWriter(s"conf/testcontent/$project.json") {write(response.json.toString()); close }
              val prs = jsonObjectMapper.getInstances[PullRequestDTO](response.json.toString())
              ProjectDTO(project, prs)
            case _ => throw new RuntimeException(s"response code was ${response.status} for project '$project'")
          }
        }
    }
  }
}

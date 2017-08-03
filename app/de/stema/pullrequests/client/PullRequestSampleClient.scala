package de.stema.pullrequests.client

import java.io.{File, FileInputStream}
import javax.inject.Inject

import de.stema.pullrequests.dto.{ProjectDTO, PullRequestDTO}
import de.stema.util.JsonObjectMapper
import play.api.libs.json.Json

import scala.concurrent.Future

/**
  * This implementation of [[de.stema.pullrequests.client.PullRequestClient]] uses a sample-json instead of
  * calling github api. That could be useful if you would like to start the
  * app without having an internet connection
  * @param jsonObjectMapper used to create an instance of [[PullRequestDTO]] from the sample json
  */
class PullRequestSampleClient @Inject()(jsonObjectMapper: JsonObjectMapper
                                       ) extends PullRequestClient {

  def getPullRequests(configuration: String): Seq[Future[ProjectDTO]] = {
    val jsonFile = new File("conf/testcontent.json")

    val stream = new FileInputStream(jsonFile)
    val json = try {
      Json.parse(stream)
    } finally {
      stream.close()
    }
    val prs = jsonObjectMapper.getInstances[PullRequestDTO](json.toString())
    Seq(Future.successful(ProjectDTO("testProject", prs)))
  }
}

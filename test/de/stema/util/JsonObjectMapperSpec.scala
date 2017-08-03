package de.stema.util

import de.stema.pullrequests.dto.{PullRequestDTO, UserDTO}
import org.scalatestplus.play.PlaySpec


class JsonObjectMapperSpec extends PlaySpec {
  "getInstance" should {
    "create instance out of json" in {
      val testee = new JsonObjectMapper

      val json =
        """
          |{
          | "title":"foo",
          | "blaa":"blubb",
          | "user": {
          |   "login":"john doe",
          |   "html_url":"www.image.com",
          |   "something_else":"we don't care about"
          | }
          |
          |}
        """.stripMargin

      val result: PullRequestDTO = testee.getInstance[PullRequestDTO](json)

      val expectedUser = new UserDTO
      expectedUser.login = "john doe"
      expectedUser.html_url = "www.image.com"
      val expected = new PullRequestDTO
      expected.title = "foo"
      expected.user = expectedUser

      result mustBe expected
    }
  }
    "getInstances" should {
      "create instance out of json" in {
        val testee = new JsonObjectMapper

        val json =
          """
            |[{
            | "title":"foo",
            | "blaa":"blubb"
            | },
            | {
            | "title":"baa",
            | "blaa":"blubb"
            | }
            | ]
          """.stripMargin

        val result: Seq[PullRequestDTO] = testee.getInstances[PullRequestDTO](json)

        val dto1 = new PullRequestDTO; dto1.title = "foo"
        val dto2 = new PullRequestDTO; dto2.title = "baa"

        val expected = Seq(dto1, dto2)
        result mustBe expected
      }
    }
}

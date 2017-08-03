package de.stema.util

import javax.inject.Singleton

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import de.stema.pullrequests.dto.PullRequestDTO

import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

@Singleton
class JsonObjectMapper {
  private lazy val mapper = new ObjectMapper with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper.configure(SerializationFeature.INDENT_OUTPUT, true)
  mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
  mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

  def getInstance[T](json: String)(implicit ct: ClassTag[T]): T =
    Try {
      mapper.readValue(json, ct.runtimeClass).asInstanceOf[T]
    } match {
      case Success(instance) => instance
      case Failure(e) => throw new IllegalStateException(s"Error during parsing of '$json'", e)
    }

  def getInstances[T](json: String)(implicit ct: ClassTag[T]): Seq[PullRequestDTO] =
    Try {
      mapper.readValue[Seq[PullRequestDTO]](json)
    } match {
      case Success(instances) => instances
      case Failure(e) => throw new IllegalStateException(s"Error during parsing of '$json'", e)
    }
}

package de.stema.pullrequests.dto

/**
  * Created by stefan on 18.07.17.
  */
class UserDTO {
  var login: String = _
  var html_url: String = _
  var avatar_url: String = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[UserDTO]

  override def equals(other: Any): Boolean = other match {
    case that: UserDTO =>
      (that canEqual this) &&
        login == that.login &&
        html_url == that.html_url &&
        avatar_url == that.avatar_url
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(login, html_url, avatar_url)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

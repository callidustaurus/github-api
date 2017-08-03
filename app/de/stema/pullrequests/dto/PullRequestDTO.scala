package de.stema.pullrequests.dto

class PullRequestDTO {

  var title: String = _
  var html_url: String = _
  var created_at: String = _
  var updated_at: String = _
  var body: String = _
  var user: UserDTO = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[PullRequestDTO]

  override def equals(other: Any): Boolean = other match {
    case that: PullRequestDTO =>
      (that canEqual this) &&
        title == that.title &&
        html_url == that.html_url &&
        created_at == that.created_at &&
        updated_at == that.updated_at &&
        body == that.body
        user == that.user
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(title, html_url, created_at, updated_at, body, user)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

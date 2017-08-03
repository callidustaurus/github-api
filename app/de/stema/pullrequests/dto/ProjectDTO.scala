package de.stema.pullrequests.dto

case class ProjectDTO(name: String, pullRequests: Seq[PullRequestDTO])

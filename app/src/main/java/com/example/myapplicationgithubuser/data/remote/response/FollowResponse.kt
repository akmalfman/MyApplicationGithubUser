package com.example.myapplicationgithubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class FollowResponse(

	@field:SerializedName("FollowResponse")
	val followResponse: List<FollowResponseItem>
)

data class FollowResponseItem(

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,
)

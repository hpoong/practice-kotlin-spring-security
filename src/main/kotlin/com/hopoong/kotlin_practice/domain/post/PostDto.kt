package com.hopoong.kotlin_practice.domain.post

import com.hopoong.kotlin_practice.domain.member.MemberDto
import com.hopoong.kotlin_practice.domain.member.toEntity


open class PostDto(
    val title: String,
    val content: String,
    val member: MemberDto
)


fun PostDto.toEntity(): Post {
    return Post(
        title = this.title,
        content = this.content,
        member = this.member.toEntity()
    )
}


class PostRequestDto {

    class PostUpdateDto(
        val id: Long,
        val title: String,
        val content: String,
        val memberId: Long,
    )


    class PostSaveDto(
        val memberId: Long,
        val title: String,
        val content: String,
    )
}

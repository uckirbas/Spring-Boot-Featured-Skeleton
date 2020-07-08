package com.example.application.batch.userimport.processors

import com.example.application.domains.users.models.dtos.UserRequest
import com.example.application.domains.users.models.entities.User
import com.example.application.domains.users.models.mappers.UserMapper
import org.springframework.batch.item.ItemProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserProcessor @Autowired constructor(
        private val userMapper: UserMapper
) : ItemProcessor<UserRequest, User> {

    override fun process(dto: UserRequest): User {
        return this.userMapper.map(dto, null)
    }

}
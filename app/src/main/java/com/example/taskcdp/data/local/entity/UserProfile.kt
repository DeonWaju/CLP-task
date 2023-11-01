package com.example.taskcdp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class UserProfile (
    @PrimaryKey
    var id: Int,

    var image: String,
    var firstName: String,
    var lastName: String,
    var email: String
)

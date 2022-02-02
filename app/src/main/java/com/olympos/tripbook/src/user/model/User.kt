package com.olympos.tripbook.src.user.model

import androidx.room.*

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    val idx: Int = 0,
    val email: String = "",
    val nickname: String = "",
)

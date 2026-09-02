package com.naveen.productivitypro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val notes: String = "",
    val dueAtMillis: Long? = null,
    val completed: Boolean = false,
    val createdAtMillis: Long = System.currentTimeMillis()
)

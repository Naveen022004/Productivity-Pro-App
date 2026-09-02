package com.naveen.productivitypro.data

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TaskTest {
    @Test
    fun newTaskIsIncomplete() {
        val task = Task(title = "Study")
        assertFalse(task.completed)
    }

    @Test
    fun completedTaskCanBeCopiedAsIncomplete() {
        val task = Task(title = "Study", completed = true)
        assertTrue(task.completed)
        assertFalse(task.copy(completed = false).completed)
    }
}

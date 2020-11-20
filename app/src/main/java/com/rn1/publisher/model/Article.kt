package com.rn1.publisher.model

import com.google.firebase.Timestamp

/**
 * firebase data class 沒給初始值會報錯!?
 */


data class Article(
    val author: String? = null,
    val title: String? = null,
    val content: String? = null,
    val category: String? = null,
    val id: String? = null,
    val createdTime: Long = System.currentTimeMillis()
)
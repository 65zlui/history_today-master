package com.animee.todayhistory.bean

import java.io.Serializable

data class HistoryBean(
    val reason: String? = null,
    val error_code: Int = 0,
    val result: List<ResultBean>? = null
) : Serializable {
    data class ResultBean(
        val day: String? = null,
        val date: String? = null,
        val title: String? = null,
        val e_id: String? = null
    ) : Serializable
}

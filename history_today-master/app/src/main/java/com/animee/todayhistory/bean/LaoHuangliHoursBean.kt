package com.animee.todayhistory.bean

import java.io.Serializable

data class LaoHuangliHoursBean(
    val reason: String? = null,
    val error_code: Int = 0,
    val result: List<ResultBean>? = null
) : Serializable {
    data class ResultBean(
        val yangli: String? = null,
        val hours: String? = null,
        val des: String? = null,
        val yi: String? = null,
        val ji: String? = null
    ) : Serializable
}

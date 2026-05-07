package com.animee.todayhistory.bean

data class LaoHuangliBean(
    val reason: String? = null,
    val result: ResultBean? = null,
    val error_code: Int = 0
) {
    data class ResultBean(
        val id: String? = null,
        val yangli: String? = null,
        val yinli: String? = null,
        val wuxing: String? = null,
        val chongsha: String? = null,
        val baiji: String? = null,
        val jishen: String? = null,
        val yi: String? = null,
        val xiongshen: String? = null,
        val ji: String? = null
    )
}

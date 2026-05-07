package com.animee.todayhistory.bean

data class HistoryDescBean(
    val reason: String? = null,
    val error_code: Int = 0,
    val result: List<ResultBean>? = null
) {
    data class ResultBean(
        val e_id: String? = null,
        val title: String? = null,
        val content: String? = null,
        val picNo: String? = null,
        val picUrl: List<PicUrlBean>? = null
    ) {
        data class PicUrlBean(
            val pic_title: String? = null,
            val id: Int = 0,
            val url: String? = null
        )
    }
}

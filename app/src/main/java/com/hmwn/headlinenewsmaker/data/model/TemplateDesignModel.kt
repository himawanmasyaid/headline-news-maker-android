package com.hmwn.headlinenewsmaker.data.model

import com.hmwn.headlinenewsmaker.R

data class TemplateDesignModel(
    var id: Int,
    var template: Int,
)

fun getTemplateDesign() = listOf(
    TemplateDesignModel(
        id = 1,
        template = R.layout.view_template_landscape_cnn
    ),
    TemplateDesignModel(
        id = 2,
        template = R.layout.view_template_landscape_bbc
    )
)

fun getDetailTemplateLayout(id: Int) : Int {
    val data = getTemplateDesign().find { it.id == id }

    return if (data != null) {
        data!!.template
    } else {
        R.layout.view_template_landscape_cnn
    }

}

fun getTemplateDesignLayout(id: Int) : TemplateDesignModel? {
    return getTemplateDesign().findLast { it.id == id }
}
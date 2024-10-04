package com.hmwn.headlinenewsmaker.data.model

import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.data.TemplateType

data class TemplateDesignModel(
    var id: Int,
    var template: Int,
    var templateType: Int = TemplateType.LANDSCAPE
)

fun getTemplateDesign() = listOf(
    TemplateDesignModel(
        id = 1,
        template = R.layout.view_template_landscape_cnn,
        templateType = TemplateType.LANDSCAPE
    ),
    TemplateDesignModel(
        id = 4,
        template = R.layout.view_template_potrait_1,
        templateType = TemplateType.POTRAIT
    ),
    TemplateDesignModel(
        id = 2,
        template = R.layout.view_template_landscape_bbc,
        templateType = TemplateType.LANDSCAPE
    ),
    TemplateDesignModel(
        id = 3,
        template = R.layout.view_template_landscape_cnn_2,
        templateType = TemplateType.LANDSCAPE
    ),

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
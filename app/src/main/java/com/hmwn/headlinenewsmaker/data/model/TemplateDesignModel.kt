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
        id = 3,
        template = R.layout.view_template_landscape_bbc,
        templateType = TemplateType.LANDSCAPE
    ),
    TemplateDesignModel(
        id = 5,
        template = R.layout.view_template_rectangle_1,
        templateType = TemplateType.RECTANGLE
    ),
    TemplateDesignModel(
        id = 6,
        template = R.layout.view_template_rectangle_2,
        templateType = TemplateType.RECTANGLE
    ),
    TemplateDesignModel(
        id = 2,
        template = R.layout.view_template_potrait_1,
        templateType = TemplateType.POTRAIT
    ),
    TemplateDesignModel(
        id = 8,
        template = R.layout.view_template_potrait_2,
        templateType = TemplateType.POTRAIT
    ),
    TemplateDesignModel(
        id = 4,
        template = R.layout.view_template_landscape_cnn_2,
        templateType = TemplateType.LANDSCAPE
    ),
    TemplateDesignModel(
        id = 7,
        template = R.layout.view_template_landscape_bbc_2,
        templateType = TemplateType.LANDSCAPE
    ),
//    TemplateDesignModel(
//        id = 9,
//        template = R.layout.view_template_mobile,
//        templateType = TemplateType.MOBILE
//    ),
)

fun getFilterByCategory(categoryId: Int): List<TemplateDesignModel> {
    val data = getTemplateDesign()
    return data.filter { it.id == categoryId }
}

fun getDetailTemplateLayout(id: Int) : Int {
    val data = getTemplateDesign().findLast { it.id == id }
    return data!!.template
}

fun getTemplateDesignLayout(id: Int) : TemplateDesignModel? {
    return getTemplateDesign().findLast { it.id == id }
}
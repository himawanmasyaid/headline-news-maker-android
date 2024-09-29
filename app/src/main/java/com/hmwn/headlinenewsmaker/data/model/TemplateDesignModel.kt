package com.hmwn.headlinenewsmaker.data.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.databinding.ViewTemplateLandscapeBbcBinding
import com.hmwn.headlinenewsmaker.databinding.ViewTemplateLandscapeCnnBinding

data class TemplateDesign(
    var id: Int,
    var template: Int,
)

fun getTemplateDesign() = listOf(
    TemplateDesign(
        id = 1,
        template = R.layout.view_template_landscape_cnn
    ),
    TemplateDesign(
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
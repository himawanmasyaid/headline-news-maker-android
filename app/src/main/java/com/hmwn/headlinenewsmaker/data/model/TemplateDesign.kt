package com.hmwn.headlinenewsmaker.data.model

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.hmwn.headlinenewsmaker.databinding.ViewTemplateLandscapeBbcBinding
import com.hmwn.headlinenewsmaker.databinding.ViewTemplateLandscapeCnnBinding

data class TemplateDesign(
    var id: Int,
    var template: ViewBinding,
) {
}

fun getTemplateDesign(inflater: LayoutInflater) = listOf(
    TemplateDesign(
        id = 1,
        template = ViewTemplateLandscapeCnnBinding.inflate(inflater)
    ),
    TemplateDesign(
        id = 2,
        template = ViewTemplateLandscapeBbcBinding.inflate(inflater)
    )
)

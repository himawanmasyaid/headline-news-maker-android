package com.hmwn.headlinenewsmaker.view.headline

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.ads.AdsManager
import com.hmwn.headlinenewsmaker.common.ImagePicker
import com.hmwn.headlinenewsmaker.common.afterTextChanged
import com.hmwn.headlinenewsmaker.common.getCurrentDateTime
import com.hmwn.headlinenewsmaker.common.startActivityLeftTransition
import com.hmwn.headlinenewsmaker.common.toast
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity
import com.hmwn.headlinenewsmaker.data.model.getDetailTemplateLayout
import com.hmwn.headlinenewsmaker.databinding.ActivityCreateHeadlineBinding
import com.hmwn.headlinenewsmaker.databinding.ViewInputHeadlineBottomDialogBinding
import com.hmwn.headlinenewsmaker.view.base.BaseActivity
import com.hmwn.headlinenewsmaker.view.preview.PreviewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import pub.devrel.easypermissions.AfterPermissionGranted
import java.io.ByteArrayOutputStream
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateHeadlineActivity : BaseActivity() {

    private val binding by lazy {
        ActivityCreateHeadlineBinding.inflate(layoutInflater)
    }

    private val viewModel: CreateHeadlineViewModel by viewModel()

    private val imagePicker by inject<ImagePicker>()

    private val adsManager by inject<AdsManager>()

    private var headline: String? = ""
    private var description: String? = ""
    private var watermark: String? = ""

    companion object {
        const val TEMPLATE_ID_ARG = "template_id"
        const val HEADLINE_ID_ARG = "headline_id"
    }

    val templateId by lazy {
        intent.getIntExtra(TEMPLATE_ID_ARG, 1)
    }

    val headlineId by lazy {
        intent.getIntExtra(HEADLINE_ID_ARG, 0)
    }

    val bottomSheetDialog by lazy {
        BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
    }

    private lateinit var tvHeadline: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvWatermark: TextView
    private lateinit var ivHeadline: ImageView
    private lateinit var containerTemplate: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initListener()
        if (headlineId != 0) {
            startObserveData()
            viewModel.getHeadlineData(headlineId)
        }
        adsManager.setupInterstitial()


        Handler(Looper.getMainLooper()).postDelayed({
            showTextBottomDialog()
        }, 600)

    }

    private fun initView() {

        with(binding) {

            try {

                val layoutResource = getDetailTemplateLayout(templateId)

                viewStub.layoutResource = layoutResource
                val inflate: View = viewStub.inflate()

                tvHeadline = inflate.findViewById(R.id.tvHeadline)
                tvDescription = inflate.findViewById(R.id.tvDescription)
                tvWatermark = inflate.findViewById(R.id.tvWatermark)
                ivHeadline = inflate.findViewById(R.id.ivHeadline)
                containerTemplate = inflate.findViewById(R.id.container)
                watermark = tvWatermark.text.toString()

            } catch (error: Exception) {

            }

        }

    }

    private fun initListener() {

        with(binding) {

            ivClose.setOnClickListener {
                finish()
            }

            tvHeadline.setOnClickListener {
                showTextBottomDialog()
            }

            tvDescription.setOnClickListener {
                showTextBottomDialog()
            }

            btnPreview.setOnClickListener {
                if (headline!!.isNotEmpty() && description!!.isNotEmpty() && watermark!!.isNotEmpty()) {
                    showInterstitialAds()
                } else {
                    toast(getString(R.string.please_input_data))
                }

            }

            ivTemplate.setOnClickListener {

            }

            ivText.setOnClickListener {
                showTextBottomDialog()
            }

            ivImage.setOnClickListener {
                showPhotoPickerDialog()
            }

        }

    }

    private fun startObserveData() {

        viewModel.headlineEntityState.observe(this) {

            if (it != null) {
                tvHeadline.text = it?.headline ?: ""
                tvDescription.text = it?.description ?: ""
                tvWatermark.text = it?.author ?: ""

                headline = it.headline
                description = it.description
                watermark =
                    if (it.author.isNullOrBlank()) getString(R.string.headline) else it.author

            }
        }

    }

    private fun navigateToPreview() {

        if (headlineId != null || headlineId != 0) {
            val request = HeadlineNewsEntity(
                id = headlineId,
                headline = headline!!,
                description = description!!,
                author = watermark ?: "",
                datetime = getCurrentDateTime(),
                image = "",
                templateId = templateId,
            )
            viewModel.updateHeadline(request)
        } else {
            val request = HeadlineNewsEntity(
                headline = headline!!,
                description = description!!,
                author = watermark ?: "",
                datetime = getCurrentDateTime(),
                image = "",
                templateId = templateId,
            )
            viewModel.insertHeadline(request)
        }

        val bitmap = getBitmapFromUiView(containerTemplate)

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        startActivityLeftTransition<PreviewActivity>(
            PreviewActivity.HEADLINE_ARG to headline,
            PreviewActivity.IMAGE_ARG to byteArray
        )

    }

    private fun showTextBottomDialog() {

        if (!bottomSheetDialog.isShowing) {

            val textBottomDialog = ViewInputHeadlineBottomDialogBinding.inflate(
                layoutInflater, null, false
            )

            bottomSheetDialog.apply {
                setCancelable(true)
                setCanceledOnTouchOutside(true)
                setContentView(textBottomDialog.root)
            }

            with(textBottomDialog) {

                etHeadline.setText(headline ?: "")
                etDescription.setText(description ?: "")
                etWatermark.setText(watermark ?: "")

                etHeadline.afterTextChanged {
                    headline = it
                    tvHeadline.text = it
                }

                etDescription.afterTextChanged {
                    description = it
                    tvDescription.text = it
                }

                etWatermark.afterTextChanged {
                    watermark = it
                    tvWatermark.text = it
                }

                tvDone.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }

            }

            bottomSheetDialog.apply {
                show()
                // Animate the dialog's behavior state change
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                            // Animation when dialog is expanded
                            textBottomDialog.root.animate().translationY(0f).setDuration(300)
                                .start()
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                })
            }
        }

    }

    fun showPhotoPickerDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkCameraPermission()
        } else {
            checkCameraAndStoragePermission()
        }
    }

    @AfterPermissionGranted(RP_CAMERA)
    fun checkCameraPermission() {
        if (hasCameraPermission()) {
            showPickImage()
        } else {
            askCameraPermission()
        }
    }

    @AfterPermissionGranted(RP_CAMERA_AND_STORAGE)
    fun checkCameraAndStoragePermission() {
        if (hasCameraAndStoragePermission()) {
            showPickImage()
        } else {
            askCameraAndStoragePermission()
        }
    }

    private fun showPickImage() {
        val intent = Intent(imagePicker.getPickImageIntent(this))
        resultImageLauncher.launch(intent)
    }

    var resultImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data

                lifecycleScope.launch {
                    val bitmapImage = withContext(Dispatchers.Default) {
                        imagePicker.getBitmapImageFromUri(uri)
                    }
                    ivHeadline.setImageBitmap(bitmapImage)
                }
            }
        }

    private fun getBitmapFromUiView(view: View): Bitmap {
        //Define a bitmap with the same size as the view
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        //Bind a canvas to it
        val canvas: Canvas = Canvas(returnedBitmap)
        //Get the view's background
        val bgDrawable = view.background
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas)
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        }
        // draw the view on the canvas
        view.draw(canvas)

        //return the bitmap
        return returnedBitmap
    }

    private fun showInterstitialAds() {

        adsManager.showInterstitialAds(this@CreateHeadlineActivity)

        // listener
        adsManager?.getInterstitial()!!.getAds()?.fullScreenContentCallback =
            object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    navigateToPreview()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    navigateToPreview()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    navigateToPreview()
                }
            }
    }

}
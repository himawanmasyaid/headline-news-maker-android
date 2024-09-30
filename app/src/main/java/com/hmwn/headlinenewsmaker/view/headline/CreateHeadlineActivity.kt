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
import androidx.viewbinding.ViewBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.ads.AdsManager
import com.hmwn.headlinenewsmaker.common.ImagePicker
import com.hmwn.headlinenewsmaker.common.afterTextChanged
import com.hmwn.headlinenewsmaker.common.toast
import com.hmwn.headlinenewsmaker.data.model.getDetailTemplateLayout
import com.hmwn.headlinenewsmaker.databinding.ActivityCreateHeadlineBinding
import com.hmwn.headlinenewsmaker.databinding.ViewInputHeadlineBottomDialogBinding
import com.hmwn.headlinenewsmaker.view.base.BaseActivity
import com.hmwn.headlinenewsmaker.view.preview.PreviewActivity
import org.koin.android.ext.android.inject
import pub.devrel.easypermissions.AfterPermissionGranted
import java.io.ByteArrayOutputStream

class CreateHeadlineActivity : BaseActivity() {

    private val binding by lazy {
        ActivityCreateHeadlineBinding.inflate(layoutInflater)
    }

    private val imagePicker by inject<ImagePicker>()

    private val adsManager by inject<AdsManager>()

    private var headline: String? = ""
    private var description: String? = ""

    companion object {
        const val TEMPLATE_ID_ARG = "template_id"
    }

    val templateId by lazy {
        intent.getIntExtra(TEMPLATE_ID_ARG, 1)
    }

    private lateinit var tvHeadline: TextView
    private lateinit var tvDescription: TextView
    private lateinit var ivHeadline: ImageView
    private lateinit var containerTemplate: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initListener()
        adsManager.setupInterstitial()

        Handler(Looper.getMainLooper()).postDelayed({
            showTextBottomDialog()
        }, 1500)

    }

    private fun initView() {

        with(binding) {

            val layoutResource = getDetailTemplateLayout(templateId)

            viewStub.layoutResource = layoutResource
            val inflatedView: View = viewStub.inflate()

            tvHeadline = inflatedView.findViewById(R.id.tvHeadline)
            tvDescription = inflatedView.findViewById(R.id.tvDescription)
            ivHeadline = inflatedView.findViewById(R.id.ivHeadline)
            containerTemplate = inflatedView.findViewById(R.id.container)

        }

    }

    private fun initListener() {

        with(binding) {

            ivClose.setOnClickListener {
                finish()
            }

            btnPreview.setOnClickListener {
                showInterstitialAds()
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

    private fun navigateToPreview() {

        if (headline!!.isNotEmpty() && description!!.isNotEmpty()) {

            val bitmap = getBitmapFromUiView(containerTemplate)

            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            val intent = Intent(this@CreateHeadlineActivity, PreviewActivity::class.java)
            intent.putExtra("image", byteArray)
            startActivity(intent)


        } else {
            toast("Please Input Data")
        }

    }

    private fun showTextBottomDialog() {

        val textBottomDialog =
            ViewInputHeadlineBottomDialogBinding.inflate(
                layoutInflater, null, false
            )

        val dialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        dialog.apply {
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            setContentView(textBottomDialog.root)
        }

        with(textBottomDialog) {

            etHeadline.afterTextChanged {
                headline = it
                tvHeadline.text = it
            }

            etDescription.afterTextChanged {
                description = it
                tvDescription.text = it
            }

            btnSave.setOnClickListener {
                dialog.dismiss()
            }

            tvDone.setOnClickListener {
                dialog.dismiss()
            }

        }

        if (!dialog.isShowing) {
            dialog.apply {
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
                ivHeadline.setImageURI(uri)
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
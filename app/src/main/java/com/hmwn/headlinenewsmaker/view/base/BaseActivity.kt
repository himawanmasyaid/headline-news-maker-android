package com.hmwn.headlinenewsmaker.view.base

import androidx.appcompat.app.AppCompatActivity
import pub.devrel.easypermissions.EasyPermissions
import android.Manifest
import android.os.Build
import android.util.Log
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.common.toast
import pub.devrel.easypermissions.AppSettingsDialog

abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    private val TAG = "BaseActivity"

    companion object {
        const val RP_CAMERA = 125
        const val RP_STORAGE = 126
        const val RP_CAMERA_AND_STORAGE = 127
    }

    private val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val CAMERA_AND_STORAGE = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)
    }

    fun hasStoragePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return true
        } else {
            return EasyPermissions.hasPermissions(this, *STORAGE)
        }

    }

    fun hasCameraAndStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(this, *CAMERA_AND_STORAGE)
    }

    fun askCameraAndStoragePermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.core_rationale_camera_and_storage),
            RP_CAMERA_AND_STORAGE,
            *CAMERA_AND_STORAGE
        )
    }

    fun askCameraPermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.core_rationale_camera),
            RP_CAMERA,
            Manifest.permission.CAMERA
        )
    }

    fun askStoragePermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.core_rationale_storage),
            RP_STORAGE,
            *STORAGE
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.e(TAG, "onPermissionsGranted: " + requestCode + " : " + perms.size)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.e(TAG, "onPermissionsDenied: " + requestCode + " : " + perms.size)

        var title = ""
        var description = ""
        when (requestCode) {
            RP_CAMERA -> toast(getString(R.string.core_permission_deny_camera))
            RP_STORAGE -> toast(getString(R.string.core_permission_deny_storage))
            RP_CAMERA_AND_STORAGE -> toast(getString(R.string.core_permission_deny_camera_and_storage))
        }

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            if (title.isNotBlank() && description.isNotBlank()) {
                AppSettingsDialog.Builder(this)
                    .setTitle(title)
                    .setRationale(description)
                    .setPositiveButton(getString(R.string.core_permission_open_settings))
                    .setNegativeButton(getString(R.string.core_permission_cancel))
                    .build().show()
            }
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d(TAG, "onRationaleAccepted: $requestCode")
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d(TAG, "onRationaleDenied: $requestCode")
    }
}
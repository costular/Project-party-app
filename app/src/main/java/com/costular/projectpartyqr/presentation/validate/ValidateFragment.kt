package com.costular.projectpartyqr.presentation.validate

import android.Manifest
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import com.costular.common.util.extensions.observe
import com.costular.projectpartyqr.R
import com.costular.projectpartyqr.common.BaseFragment
import com.costular.projectpartyqr.data.model.ValidationResult
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import com.kotlinpermissions.KotlinPermissions
import kotlinx.android.synthetic.main.fragment_validate.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.Instant

class ValidateFragment : BaseFragment(R.layout.fragment_validate),
    QRCodeReaderView.OnQRCodeReadListener {

    val validateViewModel: ValidateViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvents()
        with(qrView) {
            setOnQRCodeReadListener(this@ValidateFragment)
            setQRDecodingEnabled(true)
            setAutofocusInterval(2000L)
        }
    }

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        text?.let {
            validateViewModel.validate(it)
        }
    }

    override fun onResume() {
        super.onResume()
        KotlinPermissions.with(requireActivity())
            .permissions(Manifest.permission.CAMERA)
            .onAccepted { qrView.startCamera() }
            .ask()
    }

    override fun onPause() {
        super.onPause()
        qrView.stopCamera()
    }

    private fun bindEvents() {
        with(validateViewModel) {
            observe(resultEvent) {
                it!!.getContentIfNotHandled()?.let {
                    handleResult(it)
                }
            }
        }
    }

    private fun handleResult(result: ValidationResult) {
        ValidationResultFragment.show(childFragmentManager, result)
    }

}
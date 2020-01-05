package com.costular.projectpartyqr.presentation.generate

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.costular.common.util.extensions.observe
import com.costular.projectpartyqr.R
import kotlinx.android.synthetic.main.fragment_view_qr.*
import net.glxn.qrgen.android.QRCode
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.costular.projectpartyqr.common.RoundedBottomSheetDialogFragment

class ViewQrFragment : RoundedBottomSheetDialogFragment() {

    companion object {

        fun show(fragmentManager: FragmentManager, userId: String) {
            ViewQrFragment().apply {
                arguments = bundleOf("user_id" to userId)
                show(fragmentManager, null)
            }
        }

    }

    val viewQrViewModel: ViewQrViewModel by viewModel()

    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readAttrs()
        bindEvents()
        bindActions()
        viewQrViewModel.loadQr(userId)
    }

    private fun readAttrs() {
        arguments?.let {
            userId = it.getString("user_id")!!
        }
    }

    private fun bindEvents() {
        with(viewQrViewModel) {
            observe(qrValue) {
                showQr(it!!)
            }
            observe(shareAction) {
                it!!.getContentIfNotHandled()?.let {
                    shareImage(it)
                }
            }
        }
    }

    private fun bindActions() {
        buttonShareQr.setOnClickListener {
            viewQrViewModel.shareQr()
        }
    }

    private fun showQr(qrValue: String) {
        val bitmap = QRCode.from(qrValue).withSize(512, 512).bitmap()
        imageResult.setImageBitmap(bitmap)

        saveImage(qrValue, bitmap)
    }

    private fun saveImage(qr: String, bitmap: Bitmap) {
        try {
            val cachePath = File(requireContext().cacheDir, "images")
            cachePath.mkdirs() // don't forget to make the directory
            val stream =
                FileOutputStream("$cachePath/$qr.png") // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun shareImage(qrValue: String) {
        val imagePath = File(requireContext().cacheDir, "images")
        val newFile = File(imagePath, "$qrValue.png")
        val contentUri =
            FileProvider.getUriForFile(requireContext(), "com.costular.projectpartyqr.fileprovider", newFile)

        if (contentUri != null) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, requireContext().contentResolver.getType(contentUri))
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
            startActivity(Intent.createChooser(shareIntent, "Escoge una app"))
        }
    }

}
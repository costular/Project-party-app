package com.costular.projectpartyqr.presentation.validate

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.fragment.app.FragmentManager
import com.costular.projectpartyqr.R
import com.costular.projectpartyqr.common.RoundedBottomSheetDialogFragment
import com.costular.projectpartyqr.data.model.Invalid
import com.costular.projectpartyqr.data.model.Valid
import com.costular.projectpartyqr.data.model.ValidationResult
import kotlinx.android.synthetic.main.fragment_validation_result.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import org.threeten.bp.format.DateTimeFormatter

class ValidationResultFragment : RoundedBottomSheetDialogFragment() {

    companion object {

        fun show(fragmentManager: FragmentManager, result: ValidationResult) {
            ValidationResultFragment().apply {
                arguments = bundleOf("result" to result)
                show(fragmentManager, null)
            }
        }

    }

    lateinit var result: ValidationResult
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_validation_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readAttrs()
        printState()
    }

    private fun readAttrs() {
        result = arguments!!.getParcelable("result")!!
    }

    private fun printState() {
        when (val final = result) {
            is Valid -> {
                textName.text = final.name
                imageResult.setImageResource(R.drawable.ic_valid)
                textError.visibility = View.GONE

                showConffeti()
            }
            is Invalid -> {
                imageResult.setImageResource(R.drawable.ic_invalid)

                if (final.exists) {
                    textName.text = final.name

                    if (final.hasJoined) {
                        textError.visibility = View.VISIBLE
                        textError.text = "El invitado ya entró a las ${final.joinedAt?.format(formatter) ?: "?"}"
                    }
                }
            }
        }
    }

    private fun showConffeti() {
        konfeti.doOnLayout {
            konfeti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setSpeed(1f, 3f)
                .setFadeOutEnabled(true)
                .setDirection(0.0, 359.0)
                .setTimeToLive(5000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(Size(10))
                .setPosition(-50f, it.width + 50f, -50f, -50f)
                .streamFor(50, 2000L)
        }
    }

}
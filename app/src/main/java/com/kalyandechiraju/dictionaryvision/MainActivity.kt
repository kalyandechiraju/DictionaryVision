package com.kalyandechiraju.dictionaryvision

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.kalyandechiraju.dictionaryvision.util.positiveButton
import com.kalyandechiraju.dictionaryvision.util.showAlertDialog
import com.kalyandechiraju.dictionaryvision.util.showProgressDialog
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    button.setOnClickListener {
      RxPaparazzo.single(this)
          .usingCamera()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe { response ->
            // See response.resultCode() doc
            if (response.resultCode() == Activity.RESULT_OK) {
              //response.targetUI().loadImage(response.data())
              Glide.with(this).asBitmap().load(response.data().file).into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                  imageView.setImageBitmap(resource)
                  handleImageProcessing(resource)
                }
              })
            } else {
              Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
            }
          }
    }

  }

  private fun handleImageProcessing(resource: Bitmap) {
    val dialog  = showProgressDialog { "Processing" }
    val image = FirebaseVisionImage.fromBitmap(resource)
    val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
    detector.processImage(image)
        .addOnSuccessListener { firebaseVisionText ->
          dialog.dismiss()
          showAlertDialog {
            setMessage(firebaseVisionText.text)
            positiveButton {  }
          }
        }
        .addOnFailureListener {
          dialog.dismiss()
          Log.e("DictionaryVision", it.localizedMessage)
        }
  }
}

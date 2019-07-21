package com.kalyandechiraju.dictionaryvision.util

import android.app.Activity
import android.app.ProgressDialog
import androidx.appcompat.app.AlertDialog
import com.kalyandechiraju.dictionaryvision.R

fun androidx.fragment.app.Fragment.showAlertDialog(dialogBuilder: AlertDialog.Builder.() -> Unit) {
  val builder = context?.let { AlertDialog.Builder(it) }
  builder?.dialogBuilder()
  val dialog = builder?.create()
  dialog?.show()
}

fun Activity.showAlertDialog(dialogBuilder: AlertDialog.Builder.() -> Unit) {
  val builder = AlertDialog.Builder(this)
  builder.dialogBuilder()
  val dialog = builder.create()
  dialog?.show()
}


fun AlertDialog.Builder.positiveButton(text: String = context.getString(R.string.dialog_button_okay), handleClick: (which: Int) -> Unit = {}) {
  this.setPositiveButton(text) { _, which -> handleClick(which) }
}

fun AlertDialog.Builder.negativeButton(text: String = context.getString(R.string.dialog_button_cancel), handleClick: (which: Int) -> Unit = {}) {
  this.setNegativeButton(text) { _, which -> handleClick(which) }
}

inline fun androidx.fragment.app.Fragment.showProgressDialog(message: () -> CharSequence): ProgressDialog {
  val dialog = ProgressDialog(context)
  dialog.setMessage(message())
  dialog.show()
  return dialog
}

inline fun Activity.showProgressDialog(message: () -> CharSequence): ProgressDialog {
  val dialog = ProgressDialog(this)
  dialog.setMessage(message())
  dialog.show()
  return dialog
}
package com.kalyandechiraju.dictionaryvision

import android.app.Application
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo

class VisionApp: Application() {
  override fun onCreate() {
    super.onCreate()
    RxPaparazzo.register(this)
  }
}
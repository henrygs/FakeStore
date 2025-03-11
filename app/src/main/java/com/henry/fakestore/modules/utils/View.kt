package com.henry.fakestore.modules.utils

import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.isVisibility(isVisible: Boolean) {
    if(isVisible) this.visibility = View.VISIBLE else this.visibility = View.GONE
}
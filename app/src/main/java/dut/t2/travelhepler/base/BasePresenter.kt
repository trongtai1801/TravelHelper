package dut.t2.travelhelper.base

import android.content.Context

abstract class BasePresenter<T : BaseView>(var context: Context) {
    protected var view: T? = null

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}
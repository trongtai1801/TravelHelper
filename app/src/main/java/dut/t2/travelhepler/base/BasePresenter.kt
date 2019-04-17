package dut.t2.travelhelper.base

import android.content.Context

abstract class BasePresenter<V : BaseView>(var context: Context) {
    protected var view: V? = null

    fun attachView(view: V) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}
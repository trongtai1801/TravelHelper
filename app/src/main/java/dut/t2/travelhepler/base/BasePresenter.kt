package dut.t2.basemvp.base

import android.content.Context

abstract class BasePresenter<T : BaseView> {
    protected var view: T? = null
    protected var context: Context? = null

    fun BasePresenter(context: Context) {
        this.context = context
    }

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}
package dut.t2.basemvp.base

interface BaseView {
    fun showToast(message: String)

    fun showLoading()

    fun dismissLoading()

    fun showError(error: String)

    fun showMessage(message: String)
}
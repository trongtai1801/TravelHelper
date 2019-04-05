package dut.t2.travelhelper.base

interface BaseView {
    fun showToast(message: String)

    fun showLoading()

    fun dismissLoading()

    fun showError(error: String)

    fun showMessage(message: String)
}
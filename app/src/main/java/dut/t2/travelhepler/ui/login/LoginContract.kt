package dut.t2.travelhepler.ui.login

import dut.t2.basemvp.base.BaseView
import dut.t2.travelhelper.service.model.User

interface LoginContract {

    interface LoginView : BaseView {
        fun loginResult()
    }

    interface LoginPresenter {
        fun login(user: User)
    }

}
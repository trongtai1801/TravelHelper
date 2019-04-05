package dut.t2.travelhelper.ui.signup

import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BaseView


interface SignUpContract {

    interface SignUpView : BaseView {
        fun signUpResult()
    }

    interface  SignUpPresenter {
        fun signUp(jsonObject: JsonObject)
    }

}
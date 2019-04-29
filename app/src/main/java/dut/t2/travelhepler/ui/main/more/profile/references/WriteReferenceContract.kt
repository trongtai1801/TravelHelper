package dut.t2.travelhepler.ui.main.more.profile.references

import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Reference

interface WriteReferenceContract {

    interface WriteReferenceView : BaseView {
        fun writeReferenceResult(reference: Reference)
    }

    interface WtireReferencePresenter {
        fun writeReference(userId: String, reference: JsonObject)
    }
}
package dut.t2.travelhepler.ui.profile.references

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Reference

interface ReferencesContract {
    interface ReferencesView : BaseView {
        fun getReferencesResult(references: ArrayList<Reference>)
    }

    interface ReferencesPresenter {
        fun getReferences()
    }
}
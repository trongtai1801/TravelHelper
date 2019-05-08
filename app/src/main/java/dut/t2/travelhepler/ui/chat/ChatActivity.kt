package dut.t2.travelhepler.ui.chat

import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_chat)
class ChatActivity : BaseActivity<ChatContract.ChatView, ChatPresenterImpl>(),
    ChatContract.ChatView {
    override fun initPresenter() {
        mPresenter = ChatPresenterImpl(this)
    }

    override fun afterViews() {

    }

}
package dut.t2.travelhepler.utils

import org.json.JSONObject
import retrofit2.Response

class Common {
    companion object {
        fun getErrorString(response: Response<*>): String {
            var message = ""
            if (response.errorBody() != null) {
                try {
                    val errorBody = JSONObject(response.errorBody()!!.string())
                    message = errorBody.getString(Constant.MESSAGE)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            return message
        }
    }
}
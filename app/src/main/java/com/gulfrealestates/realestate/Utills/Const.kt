package com.gulfrealestates.realestate.Utills

object Const {

    const val OPEN_MAP_ACTIVITY = 1
    const val PICK_FILE_REQUEST_CODE = 2
    const val MY_PERMISSIONS_REQUEST_LOCATION = 99


    const val REMOTE_MSG_AUTHORIZATION = "Authorization"
    const val REMOTE_MSG_CONTENT_TYPE = "Content-Type"
    const val REMOTE_MSG_DATA = "data"
    const val REMOTE_MSG_REGISTRATION_IDS = "registration_ids"
    const val REMOTE_MSG_INVITER_TOKEN = "inviterToken"

    fun getRemoteMessageHeaders(): HashMap<String, String> {
        val headers = HashMap<String, String>()
        headers[REMOTE_MSG_AUTHORIZATION] = "key=AAAAIpbcFWg:APA91bG14HyBgQK4_c-0wXzBygKGLO0rAzYpwXA6rtUgMnz2AB0O4uJk-esjBPIAUoHYUWRCi9y-znmc6XkPD4YHbJD_0veEK93WJBGD5_MOpAgE64MkQaAaG61MXkyEf6ROoBGJRvY4"
        headers[REMOTE_MSG_CONTENT_TYPE] = "application/json"
        return headers
    }
}
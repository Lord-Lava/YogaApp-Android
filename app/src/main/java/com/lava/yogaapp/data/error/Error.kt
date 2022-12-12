package com.lava.yogaapp.data.error

class Error(val code: Int, val description: String) {
    constructor(
        exception: Exception,
    ) : this(code = DEFAULT_ERROR,
        description = exception.message ?: "")
}

const val NO_INTERNET_CONNECTION = -1
const val NETWORK_ERROR = -2
const val DEFAULT_ERROR = -3
const val PASSWORD_ERROR = -101
const val USER_NAME_ERROR = -102
const val CHECK_LOGIN_FIELDS = -103
const val SEARCH_ERROR = -104
const val NAME_ERROR = -105
const val AMOUNT_ERROR = -106
const val CHECK_REGISTRATION_FIELDS = -107
const val UNAUTHORIZED = 401
const val PAYMENT_ERROR = 402
const val UNKNOWN_ERROR = 409
const val BAD_REQUEST = 400
const val NOT_FOUND = 404
const val FORBIDDEN = 403
const val NOT_ACCEPTABLE = 406

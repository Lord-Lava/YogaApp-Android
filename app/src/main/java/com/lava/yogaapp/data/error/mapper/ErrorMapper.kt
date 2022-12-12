package com.lava.yogaapp.data.error.mapper

import android.content.Context
import com.lava.yogaapp.R
import com.lava.yogaapp.data.error.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMapper @Inject constructor(@ApplicationContext val context: Context) :
    ErrorMapperSource {

    override fun getErrorString(errorId: Int): String {
        return context.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
            Pair(NETWORK_ERROR, getErrorString(R.string.network_error)),
            Pair(PASSWORD_ERROR, getErrorString(R.string.invalid_password)),
            Pair(USER_NAME_ERROR, getErrorString(R.string.invalid_username)),
            Pair(CHECK_LOGIN_FIELDS, getErrorString(R.string.invalid_username_and_password)),
            Pair(SEARCH_ERROR, getErrorString(R.string.search_error)),
            Pair(NAME_ERROR, getErrorString(R.string.name_error)),
            Pair(AMOUNT_ERROR, getErrorString(R.string.amount_error)),
            Pair(PAYMENT_ERROR, getErrorString(R.string.payment_failed)),
            Pair(UNKNOWN_ERROR, getErrorString(R.string.unknown_error)),
            Pair(BAD_REQUEST, getErrorString(R.string.bad_request)),
            Pair(NOT_FOUND, getErrorString(R.string.not_found)),
            Pair(FORBIDDEN, getErrorString(R.string.forbidden)),
            Pair(NOT_ACCEPTABLE, getErrorString(R.string.not_acceptable)),
            Pair(CHECK_REGISTRATION_FIELDS, getErrorString(R.string.invalid_name_and_amount)),
            Pair(UNAUTHORIZED, getErrorString(R.string.unauthorized))
        ).withDefault { getErrorString(R.string.network_error) }
}

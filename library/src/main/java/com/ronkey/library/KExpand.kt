package com.ronkey.library

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.util.*

/**
 * kotlin expand
 * @date 2022/9/8
 * @author RonKey
 * @since v1.0
 */
//-----view
@SuppressLint("ResourceAsColor")
fun TextView.selectedStyle(@ColorRes backgroundColor: Int, @ColorRes textColor: Int) {
    setTextColor(textColor)
    setBackgroundColor(backgroundColor)
}

@SuppressLint("ResourceAsColor")
fun TextView.normalStyle(@ColorRes backgroundColor: Int, @ColorRes textColor: Int) {
    setTextColor(textColor)
    setBackgroundColor(backgroundColor)
}
//----- thread
/**
 * run launch io
 *
 */
class CoroutinesExpand {
    fun CoroutineScope.launchIO(
        block: suspend () -> Unit,
        error: suspend (Throwable) -> Unit = { it.printStackTrace() }
    ): Job {
        return launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Throwable) {
                error(e)
                e.printStackTrace()
            }
        }
    }
}

/**
 * run main thread
 * @param block suspend code
 */
suspend fun runMainThread(
    block: suspend () -> Unit
) = withContext(Dispatchers.Main) {
    block()
}

/**
 * run io thread
 */
suspend fun runIOThread(
    block: suspend () -> Unit
) = withContext(Dispatchers.IO) {
    block()
}


fun Date.calendar(): Calendar {
    val instance = Calendar.getInstance()
    instance.time = this
    return instance
}

fun String.getObjectTime(): Date {
    val time = "${Integer.parseInt(this.substring(0, 8), 16)}000".toLong()
    return Date(time)
}

//-----ViewModel
fun ViewModel.launchIO(
    block: suspend () -> Unit,
    error: suspend (Throwable) -> Unit = { it.printStackTrace() }
) = viewModelScope.launch(Dispatchers.IO) {
    try {
        block()
    } catch (e: Throwable) {
        error(e)
        e.printStackTrace()
    }
}

fun ViewModel.launch(
    block: suspend () -> Unit,
    error: suspend (Throwable) -> Unit = { it.printStackTrace() }
) = viewModelScope.launch() {
    try {
        block()
    } catch (e: Throwable) {
        error(e)
        e.printStackTrace()
    }
}

/**
 * ViewModel new thread
 *
 * @param block
 * @param error
 * @return
 */
@kotlinx.coroutines.ObsoleteCoroutinesApi
fun ViewModel.launchNewThread(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) =
    viewModelScope.launch(
        newSingleThreadContext("newThread${System.currentTimeMillis()}")
    ) {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
            e.printStackTrace()
        }
    }
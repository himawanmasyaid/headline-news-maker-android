package com.hmwn.headlinenewsmaker.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable

inline fun <reified T : Activity> Fragment.startActivityOutRightTransition(vararg params: Pair<String, Any?>) {
    context?.let { context ->
        startActivity(createIntent(context, T::class.java, params))
    }
}

fun Activity.startActivityOutRightTransition(intent: Intent) {
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.startActivityOutRightTransition(vararg params: Pair<String, Any?>) {
    startActivity(createIntent(this, T::class.java, params))
}

inline fun <reified T : Activity> Activity.startActivityLeftTransition(vararg params: Pair<String, Any?>) {
    startActivity(createIntent(this, T::class.java, params))
}

fun Activity.startActivityLeftTransition(intent: Intent) {
    startActivity(intent)
}

inline fun <reified T : Activity> Fragment.startActivityLeftTransition(vararg params: Pair<String, Any?>) {
    context?.let { context ->
        startActivity(createIntent(context, T::class.java, params))
    }
}

inline fun <reified T : Activity> Fragment.startActivityBottomTransition(vararg params: Pair<String, Any?>) {
    context?.let { context ->
        startActivity(createIntent(context, T::class.java, params))
    }
}

inline fun <reified T : Activity> Activity.startActivityTopTransition(vararg params: Pair<String, Any?>) {
    startActivity(createIntent(this, T::class.java, params))
}

inline fun <reified T : Activity> Fragment.startActivityTopTransition(vararg params: Pair<String, Any?>) {
    context?.let { context ->
        startActivity(createIntent(context, T::class.java, params))
    }
}

fun Activity.startActivityForResultLeftTransition(intent: Intent, requestCode: Int) {
    startActivityForResult(intent, requestCode)
}

inline fun <reified T : Activity> Fragment.startActivityFadeIn(vararg params: Pair<String, Any?>) {
    context?.let { context ->
        startActivity(createIntent(context, T::class.java, params))
    }
}

inline fun <reified T : Activity> Activity.startActivityFadeIn(vararg params: Pair<String, Any?>) {
    startActivity(createIntent(this, T::class.java, params))
}

fun Activity.startActivityFadeIn(intent: Intent) {
    startActivity(intent)
}

fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

fun Intent.clearTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }



fun <T> createIntent(
    ctx: Context,
    clazz: Class<out T>,
    params: Array<out Pair<String, Any?>> = arrayOf()
): Intent {
    val intent = Intent(ctx, clazz)
    if (params.isNotEmpty()) fillIntentArguments(intent, params)
    return intent
}

private fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
    params.forEach {
        when (val value = it.second) {
            null -> intent.putExtra(it.first, null as Serializable?)
            is Int -> intent.putExtra(it.first, value)
            is Long -> intent.putExtra(it.first, value)
            is CharSequence -> intent.putExtra(it.first, value)
            is String -> intent.putExtra(it.first, value)
            is Float -> intent.putExtra(it.first, value)
            is Double -> intent.putExtra(it.first, value)
            is Char -> intent.putExtra(it.first, value)
            is Short -> intent.putExtra(it.first, value)
            is Boolean -> intent.putExtra(it.first, value)
            is Serializable -> intent.putExtra(it.first, value)
            is Bundle -> intent.putExtra(it.first, value)
            is Parcelable -> intent.putExtra(it.first, value)
            is Parcelable -> intent.putExtra(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> intent.putExtra(it.first, value)
            is LongArray -> intent.putExtra(it.first, value)
            is FloatArray -> intent.putExtra(it.first, value)
            is DoubleArray -> intent.putExtra(it.first, value)
            is CharArray -> intent.putExtra(it.first, value)
            is ShortArray -> intent.putExtra(it.first, value)
            is BooleanArray -> intent.putExtra(it.first, value)
            is ByteArray -> intent.putExtra(it.first, value)
            else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
        return@forEach
    }
}
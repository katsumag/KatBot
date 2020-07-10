package me.katsumag.katbot.extensions

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.requests.RestAction
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun Message.noMentionedMembers(): Boolean {
    return this.mentionedMembers.isEmpty()
}

suspend fun <T> RestAction<T>.await(): T
{
    return suspendCoroutine { cont ->
        this.queue({ cont.resume(it) }, { cont.resumeWithException(it) })
    }
}
package com.renaudfavier.learnbasque.core.model.data.util

data class Id<Type>(val raw: String) {

    override fun toString() = raw

    override fun equals(other: Any?): Boolean {
        return raw == other || raw == (other as? Id<*>)?.raw
    }

    override fun hashCode(): Int {
        return raw.hashCode()
    }
}

fun <Type> String.toId(): Id<Type> = Id(this)
fun <Type> Iterable<String>.toIds(): List<Id<Type>> = map(String::toId)
fun <Type> Iterable<String>.toIdSet(): Set<Id<Type>> = mapTo(mutableSetOf(), String::toId)
val Iterable<Id<*>>.raws get() = map { it.raw }
val Iterable<Id<*>>.rawSet get() = mapTo(mutableSetOf(), { it.raw })

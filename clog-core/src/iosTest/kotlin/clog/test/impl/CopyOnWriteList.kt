package clog.test.impl

import kotlin.native.concurrent.AtomicInt
import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

// mostly copied from Stately
// https://github.com/touchlab/Stately/blob/81dc7bb86d9a22d9398e9693d4606af76c90c543/stately-collections/src/nativeCommonMain/kotlin/co/touchlab/stately/collections/CopyOnWriteList.kt
actual class CopyOnWriteList<T>(elements: Collection<T>) : MutableList<T> {

    private val listData = AtomicReference<List<T>>(ArrayList<T>(elements).freeze())

    init {
        freeze()
    }

    actual constructor() : this(ArrayList<T>(0))
    constructor(initialCapacity: Int = 0) : this(ArrayList<T>(initialCapacity))

    private inline fun <R> modifyList(proc: (MutableList<T>) -> R): R {

        try {
            val mutableList = ArrayList(listData.value)
            val result = proc(mutableList)
            listData.value = mutableList.freeze()

            return result
        } finally {
        }
    }

    override val size: Int
        get() = listData.value.size

    override fun contains(element: T): Boolean = listData.value.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = listData.value.containsAll(elements)

    override fun get(index: Int): T = listData.value.get(index)

    override fun indexOf(element: T): Int = listData.value.indexOf(element)

    override fun isEmpty(): Boolean = listData.value.isEmpty()

    override fun iterator(): MutableIterator<T> =
        LocalIterator(listData.value)

    override fun lastIndexOf(element: T): Int = listData.value.lastIndexOf(element)

    override fun add(element: T): Boolean = modifyList { it.add(element) }

    override fun add(index: Int, element: T) = modifyList { it.add(index, element) }

    override fun addAll(index: Int, elements: Collection<T>): Boolean = modifyList { it.addAll(index, elements) }

    override fun addAll(elements: Collection<T>): Boolean = modifyList { it.addAll(elements) }

    override fun clear() = modifyList { it.clear() }

    override fun listIterator(): MutableListIterator<T> =
        LocalListIterator(listData.value)

    override fun listIterator(index: Int): MutableListIterator<T> =
        LocalListIterator(listData.value, index)

    override fun remove(element: T): Boolean = modifyList { it.remove(element) }

    override fun removeAll(elements: Collection<T>): Boolean = modifyList { it.removeAll(elements) }

    override fun removeAt(index: Int): T = modifyList { it.removeAt(index) }

    override fun retainAll(elements: Collection<T>): Boolean = modifyList { it.retainAll(elements) }

    override fun set(index: Int, element: T): T = modifyList { it.set(index, element) }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = modifyList { it.subList(fromIndex, toIndex) }

    private open class LocalIterator<T>(private val list: List<T>, startIndex: Int = 0) : MutableIterator<T> {
        val index = AtomicInt(startIndex)
        override fun hasNext(): Boolean = list.size > index.value

        override fun next(): T = list.get(index.addAndGet(1) - 1)

        override fun remove() {
            throw UnsupportedOperationException("Can't mutate list from iterator")
        }
    }

    private class LocalListIterator<T>(private val list: List<T>, startIndex: Int = 0) :
        LocalIterator<T>(list, startIndex), MutableListIterator<T> {
        override fun hasPrevious(): Boolean = index.value > 0

        override fun nextIndex(): Int = index.value

        override fun previous(): T = list.get(index.addAndGet(-1))

        override fun previousIndex(): Int = index.value - 1

        override fun add(element: T) {
            throw UnsupportedOperationException()
        }

        override fun set(element: T) {
            throw UnsupportedOperationException()
        }
    }
}

package android.hromovych.com.routineplanner.utils


fun <T> List<T>.mutate(op: (T) -> T) {
    val li = this.listIterator() as MutableListIterator
    while (li.hasNext()) {
        li.set(op(li.next()))
    }
}

fun <T> List<T>.mutateIndexed(op: (T, Int) -> T) {
    val li = this.listIterator() as MutableListIterator
    li.withIndex().let {
        while (it.hasNext()) {
            val item = it.next()
            li.set(op(item.value, item.index))
        }
    }
}

infix fun String?.replaceIfNullOrEmpty(replacement: String): String =
    if (this == null || this.isEmpty()) replacement else this
package android.hromovych.com.routineplanner.basic

class BasicCheckBoxListener<T>(val clickListener: (item: T, checked: Boolean) -> Unit) {

    fun onClick(item: T, checked: Boolean) = clickListener(item, checked)
}
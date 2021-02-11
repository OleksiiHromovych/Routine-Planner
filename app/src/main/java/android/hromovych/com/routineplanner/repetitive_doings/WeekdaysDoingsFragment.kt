package android.hromovych.com.routineplanner.repetitive_doings

import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.databases.labs.WeekDayLab
import android.hromovych.com.routineplanner.defaults.RecyclerWithHeaderFragment
import android.hromovych.com.routineplanner.doings.Doing
import android.hromovych.com.routineplanner.utils.rotate
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import java.text.DateFormatSymbols

class WeekdaysDoingsFragment : RecyclerWithHeaderFragment() {
    private lateinit var radioGroupLayout: RadioGroup
    private var selectedWeekDay: Int = 2 //2 is Monday
    private lateinit var weekDayLab: WeekDayLab

    override fun getDoings(): List<Doing> = weekDayLab.getDoings(selectedWeekDay)

    override fun addNewDoing(doing: Doing): Long = weekDayLab.addNewDoing(doing, selectedWeekDay)

    override fun deleteDoing(doing: Doing): Int = weekDayLab.deleteDoing(doing, selectedWeekDay)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weekDayLab = WeekDayLab(requireContext())
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = super.onCreateView(inflater, container, savedInstanceState)
        
        radioGroupLayout = RadioGroup(context).apply {
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER
            setPadding(7)
            orientation = RadioGroup.HORIZONTAL
        }
        DateFormatSymbols.getInstance().shortWeekdays
            .mapIndexed { index, s -> Pair(index, s) }
            .drop(1)
            .rotate(-1)
            .forEach { indexAndTitle: Pair<Int, String> ->  // index help to compare with current week day
                val button =
                    inflater.inflate(R.layout.radio_button, container, false) as RadioButton
                radioGroupLayout.addView(
                    button.apply {
                        id = indexAndTitle.first
                        text = indexAndTitle.second
                        setOnClickListener { onWeekDayClickAction(indexAndTitle.first) }
                    })
            }
        radioGroupLayout.check(selectedWeekDay)
        headerLayout.addView(radioGroupLayout)

        return view
    }

    private fun onWeekDayClickAction(dayIndex: Int) {
        selectedWeekDay = dayIndex
        adapter?.doings = getDoings()
    }
}
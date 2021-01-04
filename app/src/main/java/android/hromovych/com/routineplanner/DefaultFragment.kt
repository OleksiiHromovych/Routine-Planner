package android.hromovych.com.routineplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class DefaultFragment : Fragment() {

    protected lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_default, container, false)

        recyclerView = v.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        v.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            onFABClickListener(view)
        }

        return v
    }

    override fun onResume() {
        super.onResume()
        updateUi()
    }

    abstract fun setAdapterToNull()

    override fun onPause() {
        super.onPause()
        setAdapterToNull()
    }

    abstract val onFABClickListener: (View) -> Unit

    protected abstract fun updateUi()

}
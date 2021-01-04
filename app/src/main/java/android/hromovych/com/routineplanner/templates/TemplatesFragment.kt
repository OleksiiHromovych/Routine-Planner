package android.hromovych.com.routineplanner.templates

import android.hromovych.com.routineplanner.DefaultFragment
import android.hromovych.com.routineplanner.Doing
import android.hromovych.com.routineplanner.toast
import android.view.View

class TemplatesFragment : DefaultFragment() {

    private var adapter: TemplatesAdapter? = null

    override fun setAdapterToNull() {
        adapter = null
    }

    override val onFABClickListener: (View) -> Unit = {
        context.toast("FAB clicked")
    }

    override fun updateUi() {
        val templates: List<Template> = getTemplates()
        if (adapter == null) {
            adapter = TemplatesAdapter(templates) {
                context.toast("Item clicked")
            }
            recyclerView.adapter = adapter
        } else {
            adapter!!.templates = templates
            adapter!!.notifyDataSetChanged()
        }

    }

    private fun getTemplates(): List<Template> {
        //Fake realization todo: database get query
        return listOf<Template>(
            Template(
                "English",
                "learn new 10 words, read chapter of english book, repeat words, writing, serial".split(
                    ", "
                ).map {
                    Doing(it)
                }),
            Template(
                "Programming",
                "read android book, read design book, code".split(", ").map {
                    Doing(it)
                }
            )
        )
    }

}
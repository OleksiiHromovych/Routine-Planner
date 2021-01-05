package android.hromovych.com.routineplanner.templates

import android.hromovych.com.routineplanner.DefaultFragment
import android.hromovych.com.routineplanner.DoingEditDialog
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.databases.TemplateLab
import android.os.Bundle
import android.view.View

class TemplatesFragment : DefaultFragment() {

    private var adapter: TemplatesAdapter? = null
    private lateinit var templateLab: TemplateLab
    override fun setAdapterToNull() {
        adapter = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        templateLab = TemplateLab(requireContext())
    }

    override val onFABClickListener: (View) -> Unit = {
        DoingEditDialog(
            requireContext(), "",
            R.string.dialog_title_create_new_template
        ) {
            val id = templateLab.insertNewTemplate(Template(name = it))
            startEditFragment(id)
        }.show()
    }

    override fun updateUi() {
        val templates: List<Template> = getTemplates()
        if (adapter == null) {
            adapter = TemplatesAdapter(templates) {
                startEditFragment(it.id)
            }

            recyclerView.adapter = adapter
        } else {
            adapter!!.templates = templates
            adapter!!.notifyDataSetChanged()
        }

    }

    private fun startEditFragment(id: Long) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, TemplateEditFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    private fun getTemplates(): List<Template> = templateLab.getTemplates()

}
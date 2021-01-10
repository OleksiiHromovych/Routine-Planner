package android.hromovych.com.routineplanner.templates

import android.hromovych.com.routineplanner.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TemplatesAdapter(var templates: List<Template>, val onItemClickAction: (Template) -> Unit) :
    RecyclerView.Adapter<TemplatesAdapter.Holder>()
{
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView = itemView.findViewById<TextView>(R.id.template_item_title_view)
        private val doingsListView = itemView.findViewById<TextView>(R.id.template_item_doings_list)
        private var template: Template? = null

        init {

            itemView.setOnClickListener {
                onItemClickAction(template!!)
            }
        }

        fun bind(template: Template) {
            this.template = template
            titleView.text = template.name
            doingsListView.text = template.doings.joinToString(";\n"){
                it.title
            }.ifEmpty { "Empty template list" }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_template, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(templates[position])
    }

    override fun getItemCount() = templates.size

}
package com.example.seamlabstask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seamlabstask.common.Utils
import com.example.seamlabstask.databinding.ItemNewsBinding
import com.example.seamlabstask.ui.model.ArticlesItem
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(
    private val articlesList: List<ArticlesItem>?,
    private val onArticleClickCallback: (position: Int, article: ArticlesItem) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>(), Filterable {

    private lateinit var binding: ItemNewsBinding

    var articles: List<ArticlesItem>? = articlesList

    class ViewHolder(rootView: ItemNewsBinding) : RecyclerView.ViewHolder(rootView.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = articles!![position]

        binding.txtTitle.text = item.title

        binding.txtDate.text = Utils.formatDate(item.publishedAt!!)
        binding.txtSourceName.text = item.source!!.name

        Glide.with(holder.itemView)
            .load(item.urlToImage)
            .into(binding.imgNews)

        binding.cvArticle.setOnClickListener {
            onArticleClickCallback.invoke(position, item)
        }
    }

    override fun getItemCount(): Int {
        return articles!!.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                articles = if (charSearch.isEmpty()) {
                    articlesList
                } else {
                    val resultList = ArrayList<ArticlesItem>()
                    for (row in articlesList!!) {
                        if (row.source!!.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = articles
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                articles = results?.values as List<ArticlesItem>
                notifyDataSetChanged()
            }
        }
    }

}

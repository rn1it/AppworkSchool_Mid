package com.rn1.publisher.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.publisher.databinding.ItemArticleLayoutBinding
import com.rn1.publisher.model.Article
import com.rn1.publisher.model.Author
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class HomeAdapter :ListAdapter<Article, RecyclerView.ViewHolder>(ArticleDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = getItem(position)
        val articleViewHolder = holder as ArticleViewHolder
        articleViewHolder.bind(article)
    }


    class ArticleViewHolder(private val binding: ItemArticleLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(article: Article) {
            binding.article = article

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val authorString = moshi.adapter(Author::class.java).fromJson(article.author)
            binding.authorNameTv.text = authorString?.name


        }

        companion object{
            fun from(parent: ViewGroup): ArticleViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemArticleLayoutBinding.inflate(layoutInflater, parent, false)
                return ArticleViewHolder(binding)
            }

        }
    }

}

class ArticleDiffCallback: DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return  oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}
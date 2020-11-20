package com.rn1.publisher.homepage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.rn1.publisher.R
import com.rn1.publisher.databinding.FragmentHomeBinding
import com.rn1.publisher.model.Article

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModelFactory = HomeViewModelFactory()
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        val db = FirebaseFirestore.getInstance()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val articlesRecyclerView = binding.articlesRv
        val adapter = HomeAdapter()

        articlesRecyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL)
        articlesRecyclerView.addItemDecoration(dividerItemDecoration)

        val colRefArticle = db.collection("articles")

        /**
         * get article start
         */
        colRefArticle.orderBy("createdTime", Query.Direction.DESCENDING).get().addOnSuccessListener {
            val articleList = it.toObjects(Article::class.java)
            if (articleList.size > 0) {
                adapter.submitList(articleList)
            }
        }


        /**
         * 下拉 reload
         */
        binding.layoutSwipeRefreshHome.setOnRefreshListener {
            binding.layoutSwipeRefreshHome.isRefreshing = true
            colRefArticle.orderBy("createdTime", Query.Direction.DESCENDING).addSnapshotListener { snapshots, error ->

                if (error != null) {
                    Log.w("error!!", "listen:error", error)
                    return@addSnapshotListener
                }

                snapshots?.let {

                    val articleList = it.toObjects(Article::class.java)
                    Log.d("articleList", "articleList = ${articleList}")

                    val mutableList = mutableListOf<Article>()
                    it.forEach {
                        mutableList.add(it.toObject(Article::class.java))
                        Log.i("REALTIMETAG", "${it.toObject(Article::class.java)}")
                    }
                    adapter.submitList(mutableList)
                    binding.layoutSwipeRefreshHome.isRefreshing = false
                }
            }
        }


        viewModel.navigateToPost.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPostFragment())
                    viewModel.onDoneNavigate()
                }
            }
        })


        return binding.root
    }
}
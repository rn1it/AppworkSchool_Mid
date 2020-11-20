package com.rn1.publisher.postpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rn1.publisher.R
import com.rn1.publisher.ViewModelFactory
import com.rn1.publisher.databinding.FragmentPostBinding

class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModelFactory = PostViewModelFactory()
        val viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel



        viewModel.postArticleSuccess.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    findNavController().navigate(PostFragmentDirections.actionGlobalHomeFragment())
                    viewModel.onNavigateDone()
                }
            }
        })

        viewModel.showToast.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    Toast.makeText(context, "author or article can not empty!", Toast.LENGTH_LONG).show()
                    viewModel.onShowToastDone()
                }
            }
        })

        return binding.root
    }
}
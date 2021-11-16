package com.example.seamlabstask.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.seamlabstask.databinding.FragmentDetailsBinding
import com.example.seamlabstask.ui.viewmodel.HomeViewModel

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val vm: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setBtnListener()
    }

    private fun initView() {
        binding.txtSourceName.text = vm.articleItem.source!!.name
        binding.txtArticleTitle.text = vm.articleItem.title.toString()

        Glide.with(requireContext())
            .load(vm.articleItem.urlToImage)
            .into(binding.imgArticleNews)

        binding.txtArticleDesc.text = vm.articleItem.description.toString()
    }

    private fun setBtnListener() {
        binding.txtArticleUrl.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(vm.articleItem.url)
            startActivity(openURL)
        }
    }
}
package com.example.seamlabstask.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.seamlabstask.R
import com.example.seamlabstask.common.AppManager.articleItem
import com.example.seamlabstask.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

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
        binding.txtSourceName.text = articleItem.source!!.name.toString()
        binding.txtArticleTitle.text = articleItem.title.toString()

        Glide.with(requireContext())
            .load(articleItem.urlToImage)
            .into(binding.imgArticleNews)

        binding.txtArticleDesc.text = articleItem.description.toString()
    }

    private fun setBtnListener() {
        binding.txtArticleUrl.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(articleItem.url)
            startActivity(openURL)
        }
    }
}
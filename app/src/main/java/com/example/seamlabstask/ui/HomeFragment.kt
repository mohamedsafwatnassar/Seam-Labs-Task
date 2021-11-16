package com.example.seamlabstask.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seamlabstask.MainActivity
import com.example.seamlabstask.ProgressHandle
import com.example.seamlabstask.databinding.FragmentHomeBinding
import com.example.seamlabstask.handleData.HandleError
import com.example.seamlabstask.ui.adapter.NewsAdapter
import com.example.seamlabstask.ui.model.ArticlesItem
import com.example.seamlabstask.ui.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val vm: HomeViewModel by activityViewModels()

    private lateinit var newsAdapter: NewsAdapter

    private val onArticleClickCallBack: (Position: Int, article: ArticlesItem) -> Unit =
        { _, article ->
            vm.articleItem = article
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.getNewsSource()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtnListener()
        subscribeData()
        //subscribeMutiData()
    }

    private fun setBtnListener() {
        binding.refreshHome.setOnRefreshListener {
            (requireActivity() as ProgressHandle).showProgressBar()
            vm.getNewsSource()
            binding.refreshHome.isRefreshing = false
        }

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newsAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun subscribeData() {
        vm.handleData.observe(viewLifecycleOwner, {
            it.let {
                when (it.getStatus()) {
                    HandleError.DataStatus.SUCCESS -> {
                        (requireActivity() as ProgressHandle).hideProgressBar()
                        binding.recyclerViewNews.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            newsAdapter = NewsAdapter(it.getData(), onArticleClickCallBack)
                            adapter = newsAdapter
                            swipeToShare()
                        }
                    }
                    HandleError.DataStatus.ERROR -> {
                        (requireActivity() as MainActivity?)!!.hideProgressBar()
                        Toast.makeText(requireContext(), it.getError(), Toast.LENGTH_SHORT).show()
                    }
                    HandleError.DataStatus.CONNECTIONERROR -> {
                        (requireActivity() as MainActivity?)!!.hideProgressBar()
                        Toast.makeText(
                            requireContext(), it.getConnectionError(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun swipeToShare() {
        val itemSwipe = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                shareNews(viewHolder.adapterPosition)
            }
        }
        val swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(binding.recyclerViewNews)
    }

    private fun getUrl(position: Int): String {
        return newsAdapter.articles!![position].url!!
    }

    fun shareNews(position: Int) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, (getUrl(position)))
        intent.type = "text/plain"
        requireContext().startActivity(Intent.createChooser(intent, "Send To"))
    }
}
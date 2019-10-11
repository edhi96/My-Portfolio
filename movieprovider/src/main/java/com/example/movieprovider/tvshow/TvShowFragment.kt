package com.example.movieprovider.tvshow


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieprovider.ContentTransaction
import com.example.movieprovider.R
import kotlinx.android.synthetic.main.fragment_tv_show.view.*

class TvShowFragment : Fragment() {

    private lateinit var mAdapter: TvShowAdapter
    private lateinit var contentTransaction: ContentTransaction
    private lateinit var rvTvList :RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tv_show, container, false)
        activity?.let {
            contentTransaction = ContentTransaction(it)
            rvTvList = view.rvTvShowList
            rvTvList.layoutManager = GridLayoutManager(it, 2)
            mAdapter = TvShowAdapter(it)
            if(contentTransaction.selectAllTvShow().isNotEmpty())
            {
                refreshData()
            }
           else{
                Toast.makeText(it,"Kosong !",Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }
    private fun refreshData(){
        activity?.let {
            contentTransaction = ContentTransaction(it)
            mAdapter.setData(contentTransaction.selectAllTvShow())
            mAdapter.notifyDataSetChanged()
            rvTvList.adapter=mAdapter
        }
    }


}

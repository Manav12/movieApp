package com.example.testapplication.Fragments


import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.testapplication.Api.BASE_BACKDROP_IMAGE_URL
import com.example.testapplication.Constant.loadImageUrl
import com.example.testapplication.Model.Result
import com.example.testapplication.databinding.FragmentDetailBinding


class DetailFragment: BaseFragment<FragmentDetailBinding>() {
    private var bundle: Result?=null
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate


    override fun setObservers() {

    }
    override fun init() {
        bundle   = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            requireArguments().getSerializable("USER",Result::class.java )!!
        }else{
            requireArguments().getSerializable("USER") as Result
        }
        bi.releaseDate.text = bundle?.release_date
       bi.movieName.text = bundle?.title
       bi.Votes.text = bundle?.vote_count.toString()
       bi.description.text = bundle?.overview
       bi.language.text = bundle?.original_language
       bi.rating.text = bundle?.vote_average.toString()
        bi.mainImage.loadImageUrl(BASE_BACKDROP_IMAGE_URL +bundle?.poster_path)

    }
    override fun setEvents() {

    }


}
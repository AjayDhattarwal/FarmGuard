package com.ar.farmguard.news.presentation.news_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ar.farmguard.news.domian.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsDetailsViewModel(
    private val newsRepository: NewsRepository
): ViewModel(){


//    private val _news = MutableStateFlow<List<News>>(emptyList())
//    val news: StateFlow<List<News>> = _news


    fun fetchNewsDetails(
        shortTag: String,
    ){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                newsRepository.getNewsDetails(shortTag)
            }
        }
    }

}
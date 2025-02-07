package com.ar.farmguard.news.presentation.state_news

import androidx.lifecycle.ViewModel
import com.ar.farmguard.news.domian.repository.NewsRepository

class StateNewsViewModel(
    private val newsRepository: NewsRepository
): ViewModel(){

    val stateNews = newsRepository.stateNews
}
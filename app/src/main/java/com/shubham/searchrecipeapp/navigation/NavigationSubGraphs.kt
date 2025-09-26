package com.shubham.searchrecipeapp.navigation

import com.shubham.media_player.navigation.MediaPlayerFeatureAPi
import com.shubham.search.navigation.SearchFeatureApi

data class NavigationSubGraphs(
    val searchFeatureApi: SearchFeatureApi,
    val mediaPlayerApi:MediaPlayerFeatureAPi
)

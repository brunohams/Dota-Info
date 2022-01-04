package com.codingwithmitch.dotainfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.codingwithmitch.core.DataState
import com.codingwithmitch.core.Logger
import com.codingwithmitch.core.ProgressBarState
import com.codingwithmitch.core.UIComponent
import com.codingwithmitch.dotainfo.R
import com.codingwithmitch.dotainfo.ui.navigation.Screen
import com.codingwithmitch.dotainfo.ui.theme.DotaInfoTheme
import com.codingwithmitch.hero_interactors.HeroInteractors
import com.codingwithmitch.ui_herodetail.HeroDetail
import com.codingwithmitch.ui_herolist.ui.HeroList
import com.codingwithmitch.ui_herolist.ui.HeroListState
import com.codingwithmitch.ui_herolist.ui.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DotaInfoTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.HeroList.route,
                    builder = {
                        addHeroList(navController, imageLoader)
                        addHeroDetail(navController)
                    }
                )
            }
        }
    }

}

fun NavGraphBuilder.addHeroList(
    navController: NavController,
    imageLoader: ImageLoader,
) {
    composable(
        route = Screen.HeroList.route
    ) {
        val viewModel: HeroListViewModel = hiltViewModel()
        HeroList(
            state = viewModel.state.value,
            imageLoader = imageLoader,
            navigateToDetailScreen = { heroId ->
                navController.navigate("${Screen.HeroDetail.route}/$heroId")
            }
        )
    }
}

fun NavGraphBuilder.addHeroDetail(
    navController: NavController
) {
    composable(
        route = Screen.HeroDetail.route + "/{heroId}",
        arguments = Screen.HeroDetail.arguments
    ) {
        HeroDetail(heroId = it.arguments?.get("heroId") as Int?)
    }
}















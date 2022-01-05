package com.codingwithmitch.dotainfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.codingwithmitch.dotainfo.ui.navigation.Screen
import com.codingwithmitch.dotainfo.ui.theme.DotaInfoTheme
import com.codingwithmitch.ui_herodetail.ui.HeroDetail
import com.codingwithmitch.ui_herodetail.ui.HeroDetailViewModel
import com.codingwithmitch.ui_herolist.ui.HeroList
import com.codingwithmitch.ui_herolist.ui.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
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
                        addHeroDetail(navController, imageLoader)
                    }
                )
            }
        }
    }

}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
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
            events = viewModel::onTriggerEvent,
            imageLoader = imageLoader,
            navigateToDetailScreen = { heroId ->
                navController.navigate("${Screen.HeroDetail.route}/$heroId")
            }
        )
    }
}

fun NavGraphBuilder.addHeroDetail(
    navController: NavController,
    imageLoader: ImageLoader,
) {
    composable(
        route = Screen.HeroDetail.route + "/{heroId}",
        arguments = Screen.HeroDetail.arguments
    ) {
        val viewModel: HeroDetailViewModel = hiltViewModel()
        HeroDetail(
            state = viewModel.state.value,
            imageLoader = imageLoader
        )
    }
}















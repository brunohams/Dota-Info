package com.codingwithmitch.dotainfo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import coil.ImageLoader
import com.codingwithmitch.dotainfo.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.navigate(R.id.heroListActivity)

//        setContent {
//            DotaInfoTheme {
//                val navController = rememberAnimatedNavController()
//                BoxWithConstraints {
//                    AnimatedNavHost(
//                        navController = navController,
//                        startDestination = Screen.HeroList.route,
//                        builder = {
//                            addHeroList(navController, imageLoader, screenWidth = constraints.maxWidth / 2)
//                            addHeroDetail(navController, imageLoader, screenWidth = constraints.maxWidth / 2)
//                        }
//                    )
//                }
//            }
//        }

    }

}

//fun NavGraphBuilder.addHeroList(
//    navController: NavController,
//    imageLoader: ImageLoader,
//    screenWidth: Int
//) {
//    composable(
//        route = Screen.HeroList.route,
//        exitTransition = { _, _ ->
//            slideOutHorizontally(
//                targetOffsetX = {-screenWidth},
//                animationSpec = tween(
//                    durationMillis = 300,
//                    easing = FastOutSlowInEasing
//                )
//            ) + fadeOut(animationSpec = tween(300))
//        },
//        popEnterTransition = { _, _ ->
//            slideInHorizontally(
//                initialOffsetX = {-screenWidth},
//                animationSpec = tween(
//                    durationMillis = 300,
//                    easing = FastOutSlowInEasing
//                )
//            ) + fadeIn(animationSpec = tween(300))
//        }
//    ) {
//        val viewModel: HeroListViewModel = hiltViewModel()
//        HeroList(
//            state = viewModel.state.value,
//            events = viewModel::onTriggerEvent,
//            imageLoader = imageLoader,
//            navigateToDetailScreen = { heroId ->
//                navController.navigate("${Screen.HeroDetail.route}/$heroId")
//            }
//        )
//    }
//}
//
//fun NavGraphBuilder.addHeroDetail(
//    navController: NavController,
//    imageLoader: ImageLoader,
//    screenWidth: Int
//) {
//    composable(
//        route = Screen.HeroDetail.route + "/{heroId}",
//        arguments = Screen.HeroDetail.arguments,
//        enterTransition = { _, _ ->
//            slideInHorizontally(
//                initialOffsetX = {screenWidth},
//                animationSpec = tween(
//                    durationMillis = 300,
//                    easing = FastOutSlowInEasing
//                )
//            ) + fadeIn(animationSpec = tween(300))
//        },
//        popExitTransition = { _, _ ->
//            slideOutHorizontally(
//                targetOffsetX = {screenWidth},
//                animationSpec = tween(
//                    durationMillis = 300,
//                    easing = FastOutSlowInEasing
//                )
//            ) + fadeOut(animationSpec = tween(300))
//        }
//    ) {
//        val viewModel: HeroDetailViewModel = hiltViewModel()
//        HeroDetail(
//            state = viewModel.state.value,
//            imageLoader = imageLoader,
//            events = viewModel::onTriggerEvent
//        )
//    }
//}















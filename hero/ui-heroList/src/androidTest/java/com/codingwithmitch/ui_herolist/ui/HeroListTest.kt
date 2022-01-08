package com.codingwithmitch.ui_herolist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import coil.ImageLoader
import com.codingwithmitch.core.domain.ProgressBarState
import com.codingwithmitch.hero_datasource_test.network.data.HeroDataValid
import com.codingwithmitch.hero_datasource_test.util.serializeHeroData
import com.codingwithmitch.ui_herolist.coil.ImageLoaderFake
import org.junit.Rule
import org.junit.Test

// TEST ISOLADO
@ExperimentalComposeUiApi
class HeroListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val imageLoader: ImageLoader = ImageLoaderFake.build(context)
    private val heroData = serializeHeroData(HeroDataValid.data)

    @ExperimentalAnimationApi
    @Test
    fun areHeroesShown() {
        composeTestRule.setContent {
            val state = remember {
                HeroListState(
                    heroes = heroData,
                    filteredHeroes = heroData
                )
            }
            HeroList(
                state = state,
                events = {},
                navigateToDetailScreen = {},
                imageLoader = imageLoader
            )
        }

        // Assert that these 3 infos are showing
        composeTestRule.onNodeWithText("Anti-Mage").assertIsDisplayed()
        composeTestRule.onNodeWithText("Axe").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bane").assertIsDisplayed()
    }
}
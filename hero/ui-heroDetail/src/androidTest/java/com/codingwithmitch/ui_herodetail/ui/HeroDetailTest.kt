package com.codingwithmitch.ui_herodetail.ui

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
import com.codingwithmitch.ui_herodetail.coil.ImageLoaderFake
import org.junit.Rule
import org.junit.Test
import kotlin.math.round
import kotlin.random.Random

// TEST ISOLADO
@ExperimentalComposeUiApi
class HeroDetailTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val imageLoader: ImageLoader = ImageLoaderFake.build(context)
    private val heroData = serializeHeroData(HeroDataValid.data)

    @ExperimentalAnimationApi
    @Test
    fun isHeroDataShown() {
        // choose random hero
        val hero = heroData[Random.nextInt(0, heroData.size - 1)]

        composeTestRule.setContent {
            val state = remember {
                HeroDetailState(
                    hero = hero
                )
            }
            HeroDetail(
                state = state,
                events = {},
                imageLoader = imageLoader
            )
        }

        // Assert that infos are showing
        composeTestRule.onNodeWithText(hero.localizedName).assertIsDisplayed()
        composeTestRule.onNodeWithText(hero.primaryAttribute.uiValue).assertIsDisplayed()
        composeTestRule.onNodeWithText(hero.attackType.uiValue).assertIsDisplayed()

        val proWinPercentage = round(hero.proWins.toDouble() / hero.proPick.toDouble() * 100).toInt()
        composeTestRule.onNodeWithText("$proWinPercentage %").assertIsDisplayed()

        val turboWinPercentage = round(hero.turboWins.toDouble() / hero.turboPicks.toDouble() * 100).toInt()
        composeTestRule.onNodeWithText("$turboWinPercentage %").assertIsDisplayed()

    }
}
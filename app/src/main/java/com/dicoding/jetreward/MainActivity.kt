package com.dicoding.jetreward

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.dicoding.jetreward.ui.theme.JetRewardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetRewardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    JetRewardApp()
                }
            }
        }
    }
}

// in Compose Testing, system use Semantic Tree to seek for component
// Semantic means "part of UI", it could be Composable or page
// Semantic Tree is a tree diagram that serve as structure of Composables within a page

/* to add semantics with better clarity, use modifier:
MyButton(
    modifier = Modifier.semantics(mergeDescendant = true) {
        contentDescription = "Add to favorites"
    }
)
*/

// to see Semantic Property of a component, use `printToLog()`:
// - composeTestRule.onRoot().printToLog("currentLabelExists")

// some component is merged by another component, which then be its children component (Button() has Text())
/* to see unmerged component, `useUnmergedTree` could be used:
composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")*/
// - merge / unmerged Tree diagram example:
// --- https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:44641355020c40cbe02a4661281d4cd120221025140255.png
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

// Navigation Component could be used to navigate between Composable
// Navigation principle:
// - every app need to have static Start Destination
// - Stack need to save state when getting back from the next page
// - UpBack & Back Button need to be right
// - on Start Destination, UpBack button not causing out from app. But Back Button / gesture need to exit from app
// - Deep Link need to have backstack flow same as manual navigation

// 3 parts of Navigation Component
// - NavHost: base container to host & display defined page (automatically Recompose when navigating)
// - NavGraph: Map navigation flow and page of an App. It contains:
// --- Destination: destination page to be accessed. all app need to have Start Destination
// --- Route: to map navigation route. It is determined by path String, like an URL
// - NavController: core class to execute navigation to route
// --- is Stateful & used to save Composable to BackStack

// Backstack is a state which shows the state that is being opened
// - when Back Button is pressed, the current page will be deleted from Stack
// - popBackStack() could be used to delete all BackStack, leaving only first page

/* example defining NavController, NavHost, and NavGraph:
val navController = rememberNavController() // make NavHostController state
NavHost( // connect NavController & NavGraph (1 NavController each NavHost)
	navController = navController,
	startDestination = "first"
) {
	composable(route = "first") { // `composable` is an extension function from NavGraphBuilder to add ComposableScreen to NavGraph
		FirstScreen()
	}
	composable(route = "second")
		SecondScreen()
	}
 }*/

// NavController implementation:
// - navigate: execute navigation to targetted route
// - navigateUp: to page before
// - popBackStack(): banish BackStack, leaving only first page
// NavController implementation diagram: https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:acde01e828e5823cd7f7c5c3e1ac474320221025132826.png

// Send / Retrieve Argument:
// - to attach argument from a Composable, use "direction/$the_item"
// - to retrieve arguement in a Composable, use "route/{the_item}"
/* example:
composable(route = "first") {
	FirstScreen { messageContent ->
		navController.navigate("second/$messageContent")
	}
}
composable(
	route = "second/{content}",
)*/

// by default argument will be sent in type of String
// - supported format are String, Integer, Float, Long, Boolean, Parcelable, Serializable, and Enum
// - to change type of argument, use `arguments` parameter, then state the type using NavType:
/* example:
composable(
	route = "second/{content}",
	arguments = listOf(
		navArgument("content") {
			type = NavType.StringType
		},
	)
) */

// if the item is called "Voyager", system will automatically make URI as such:
// - android-app://androidx.navigation/second/Voyager

// to add another argument, add more slash `/`
// to add optional argument, add question mark `?` and query name
// - add ampersand `&` if there is another optional argument
/* example:
composable(route = "first") {
	FirstScreen { messageContent ->
		navController.navigate("second/$messageContent/10?optionalContent=Welcome")
	}
}
composable(
	route = "second/{content}/{otherContent}?optionalContent={optionalContent}&otherOptionalContent={otherOptionalContent}",
	arguments = listOf(
		navArgument("content") {
			type = NavType.StringType
		},
		navArgument("otherContent") {
			type = NavType.IntType
		},
		navArgument("optionalContent") {
			type = NavType.StringType
			nullable = true
		},
		navArgument("otherOptionalContent") {
			type = NavType.StringType
			defaultValue = "!"
		}
	)
) */

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
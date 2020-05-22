package com.test.jokes.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment

/**
 * Navigate to a destination from the current navigation graph. This supports both navigating
 * via an {@link NavDestination#getAction(int) action} and directly navigating to a destination.
 * If the navigation target could not be found, the method returns false.
 *
 * @return true, if the navigation target could be found, otherwise false
 * @param resId an {@link NavDestination#getAction(int) action} id or a destination id to
 *              navigate to
 * @param args arguments to pass to the destination
 * @param navOptions special options for this navigation operation
 * @param navigatorExtras extras to pass to the Navigator
 */
fun NavController.navigateIfHasAction(
    resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
): Boolean {
    // this is identical almost to the original navigate method. We have to adjust it slightly because we have no access to all properties
    var destId = resId
    // if the top element of the stack is null, we are using the graph itself
    val currentNode: NavDestination = currentDestination ?: graph

    val navAction = currentNode.getAction(resId)
    if (navAction != null) {
        // we don't change the navOptions here, because we have to pass them unchanged to the navigate method
        destId = navAction.destinationId
    }

    if (destId == 0 && navOptions != null && navOptions.popUpTo != 0) {
        popBackStack(navOptions.popUpTo, navOptions.isPopUpToInclusive)
        return true
    }

    if (destId == 0) {
        throw IllegalArgumentException("Destination id == 0 can only be used" + " in conjunction with navOptions.popUpTo != 0")
    }

    // we have no access to the original findDestination method, so we use our own implementation
    val node = findDestinationSafe(destId)

    // if the node is null, the NavController throws usually an error. We avoid this and return false instead.
    node?.run {
        navigate(resId, args, navOptions, navigatorExtras)
        return true
    }

    return false
}

// we have to copy the original method findDestination because we have no access to it.
private fun NavController.findDestinationSafe(@IdRes destinationId: Int): NavDestination? {
    if (graph.id == destinationId) {
        return graph
    }
    val currentNode = currentDestination ?: graph
    val currentGraph = currentNode as? NavGraph ?: currentNode.parent
    return currentGraph?.findNode(destinationId)
}

fun Fragment.findNavController(): NavController =
    NavHostFragment.findNavController(this)
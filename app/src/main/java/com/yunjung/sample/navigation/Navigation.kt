package com.yunjung.sample.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.yunjung.sample.R
import com.yunjung.sample.presentation.home.HomeFragment
import com.yunjung.sample.presentation.login.LoginFragment
import com.yunjung.sample.presentation.splash.SplashFragment

class Navigation(activity: FragmentActivity) {
    private var activity: FragmentActivity
    private var navHostFragment: NavHostFragment
    private var navController: NavController

    init {
        this.activity = activity
        navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        initNavGraph()
    }

    private fun initNavGraph(){
        navController.graph = navController.createGraph(
            startDestination = NavDest.splash
        ){
            fragment<SplashFragment>(NavDest.splash){
                label = NavDest.splash
            }
            fragment<LoginFragment>(NavDest.login){
                label = NavDest.login
            }
            fragment<HomeFragment>(NavDest.home) {
                label = NavDest.home
            }
        }
    }

    fun navigate(navDest: String){
        navController.navigate(navDest)
    }

    fun getLastFragment(): Fragment = navHostFragment.childFragmentManager.fragments[0]

    fun setDestinationChangedListener(
        callback: (controller: NavController, dest: NavDestination, args: Bundle?) -> Unit
    ){
        navController.addOnDestinationChangedListener(callback)
    }
}

object NavDest{
    const val splash = "splash"
    const val login = "login"
    const val home = "home"
}
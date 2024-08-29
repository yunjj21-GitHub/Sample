package com.yunjung.sample.navigation

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.yunjung.sample.R
import com.yunjung.sample.presentation.login.LoginFragment
import com.yunjung.sample.presentation.splash.SplashFragment

object Navigation {
    private lateinit var activity: FragmentActivity
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    fun setNavigation(activity: FragmentActivity){
        this.activity = activity
        initNavHostFragment()
        initNavController()
        initNavGraph()
    }

    private fun initNavHostFragment(){
        navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
    }

    private fun initNavController(){
        navController = navHostFragment.navController
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
        }
    }

    fun navigate(navDest: String){
        navController.navigate(navDest)
    }
}

object NavDest{
    const val splash = "splash"
    const val login = "login"
}
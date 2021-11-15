package com.example.seamlabstask

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.seamlabstask.broadcast.ConnectivityReceiver
import com.example.seamlabstask.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener,
    ProgressHandle {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize navigation component
        setNavComponent()

        // initialize Snack bar
        initSnackBar()

        // initialize Broadcast Receiver
        initBroadcastReceiver()
    }

    private fun setNavComponent() {
        val navHostFrag =
            supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
        navController = navHostFrag.navController
        navController.setGraph(R.navigation.nav_graph, intent.extras)
        //navController.addOnDestinationChangedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun showProgressBar() {
        binding.progressBar.root.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.root.visibility = View.GONE
    }

    private fun initSnackBar() {
        snackBar = Snackbar.make(
            binding.root, getString(R.string.checkConnection), Snackbar.LENGTH_INDEFINITE
        )
    }

    private fun initBroadcastReceiver() {
        registerReceiver(
            ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            snackBar!!.show()
        } else if (isConnected) {
            if (snackBar!!.isShown) {
                snackBar!!.dismiss()
            }
        }
    }
}
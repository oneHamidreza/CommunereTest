package communere.ui.main

import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import communere.R
import communere.data.DataSource
import communere.databinding.ActivityMainBinding
import communere.ui.home.HomeViewModel
import communere.widget.navheader.NavHeaderView
import meow.core.ui.MeowActivity
import meow.ktx.getColorCompat
import meow.ktx.instanceViewModel
import meow.ktx.sdkNeed
import org.kodein.di.erased.instance

/**
 * Main Activity class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class MainActivity : MeowActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var navHeaderView: NavHeaderView? = null

    private val dataSource by instance<DataSource>()

    private val viewModel: MainViewModel by instanceViewModel()
    private val homeViewModel: HomeViewModel by instanceViewModel()

    override fun layoutId() = R.layout.activity_main

    override fun initViewModel() {
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbar)
        setupNavigation()
        viewModel.setup()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.navHost).apply {
            addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.fragmentLogin -> {
                        binding.toolbar.visibility = View.GONE
                        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    }
                    else -> {
                        binding.toolbar.visibility = View.VISIBLE
                        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    }
                }
                updateNavigationHeader()
            }
        }
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.apply {
            drawerLayout.setStatusBarBackgroundColor(getColorCompat(R.color.status_bar))
            navHeaderView = NavHeaderView(context())
            navigationView.addHeaderView(navHeaderView!!)
            updateNavigationHeader()

            sdkNeed(21) {
                navigationView.setItemBackgroundResource(R.drawable.navigation_view_item_background)
            }

            navigationView.setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.actionToHome -> navController.navigate(R.id.fragmentHome)
                    R.id.actionToLogout -> {
//                        alert()
//                            .setTitle(R.string.logout_alert_title)
//                            .setMessage(R.string.logout_alert_message)
//                            .setPositiveButton(R.string.yes){ _,_ ->
//                                viewModel?.logout()
//                                recreate()
//                            }
//                            .setNegativeButton(R.string.no){_,_ ->
//
//                            }
//                            .show()
                    }
                }
                navigationView.isEnabled = false
                drawerLayout.closeDrawers()
                true
            }
        }
    }

    private fun updateNavigationHeader() {
        navHeaderView?.apply {
            val user = dataSource.fetchUser()
            avatarUrl = user.avatarUrl
            alias = user.alias
            caption = user.username
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp())
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

}
package pe.upc.edu.easyFinance.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import pe.upc.edu.easyFinance.view.fragments.GoalFragment
import pe.upc.edu.easyFinance.R
import pe.upc.edu.easyFinance.view.fragments.BudgetFragment
import pe.upc.edu.easyFinance.view.fragments.DashboardFragment
import kotlinx.android.synthetic.main.activity_main.*
import pe.upc.edu.easyFinance.model.persistence.AppDatabase
import pe.upc.edu.easyFinance.model.persistence.model.User
import pe.upc.edu.easyFinance.view.adapters.SectionPageAdapter

class MainActivity : AppCompatActivity() {

    private var backPress = 0
    lateinit var toolbar: Toolbar
    var users: MutableList<User> = ArrayList()
    var colorIntArray: List<Int> = arrayListOf(R.color.primary, R.color.primaryDark, R.color.warningError)
    var iconIntArray: List<Int> = arrayListOf(R.drawable.ic_add, R.drawable.ic_add, R.drawable.ic_add)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region TabBar
        val viewPager: ViewPager = findViewById(R.id.nav_view)

        setupViewPager(viewPager)
        tabs.setupWithViewPager(viewPager)
        //endregion

        //region fbAnimation
        val onTabSelectedListener: TabLayout.OnTabSelectedListener = object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
                animateFab(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        }

        tabs.addOnTabSelectedListener(onTabSelectedListener)
        //endregion

        //region ToolBar
        toolbar = findViewById(R.id.toolbar_home)
        setSupportActionBar(toolbar)

        supportActionBar?.title = resources.getString(R.string.title_home)
        //endregion

        users = AppDatabase.getInstance(applicationContext).getDao().getAll()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.bt_logout -> {
                val intent = Intent(this, SignInActivity::class.java)
                logOut()
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }

    override fun onPause() {
        val viewPager: ViewPager = findViewById(R.id.nav_view)
        viewPager.currentItem = 0
        super.onPause()
    }

    override fun onStop() {
        val viewPager: ViewPager = findViewById(R.id.nav_view)
        viewPager.currentItem = 0
        super.onStop()
    }

    override fun onBackPressed() {
        backPress += 1
        if (backPress > 1) {
            this.finishAffinity()
        } else {
            Toast.makeText(
                applicationContext,
                resources.getText(R.string.exit_text),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionPageAdapter(supportFragmentManager)
        adapter.addFragment(DashboardFragment(), resources.getString(R.string.title_dashboard))
        adapter.addFragment(GoalFragment(), resources.getString(R.string.title_goals))
        adapter.addFragment(BudgetFragment(), resources.getString((R.string.title_budget)))
        viewPager.adapter = adapter
    }

    private fun animateFab(position: Int) {
        fab.clearAnimation()
        val shrink = ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f)
        shrink.duration = 100
        shrink.interpolator = DecelerateInterpolator()
        shrink.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                fab.backgroundTintList = resources.getColorStateList(colorIntArray[position])
                fab.setImageDrawable(resources.getDrawable(iconIntArray[position]))

                val expand = ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f)
                expand.duration = 100
                expand.interpolator = AccelerateInterpolator()
                fab.startAnimation(expand)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        fab.startAnimation(shrink)
    }

    private fun logOut(){
        val index = users[0].id
        var user = User(index, "", false)
        AppDatabase.getInstance(applicationContext).getDao().update(user)
    }
}

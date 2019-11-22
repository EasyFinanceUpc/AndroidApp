package pe.upc.edu.easyFinance.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import pe.upc.edu.easyFinance.fragments.GoalFragment
import pe.upc.edu.easyFinance.R
import pe.upc.edu.easyFinance.adapters.SectionsPagerAdapter
import pe.upc.edu.easyFinance.fragments.BudgetFragment
import pe.upc.edu.easyFinance.fragments.DashboardFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var backPress = 0
    lateinit var toolbar: Toolbar
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
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = resources.getString(R.string.title_home)
        //endregion
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
        //var adapter = SectionsPagerAdapter(applicationContext, supportFragmentManager)
        val adapter = SectionsPagerAdapter(supportFragmentManager)
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
}

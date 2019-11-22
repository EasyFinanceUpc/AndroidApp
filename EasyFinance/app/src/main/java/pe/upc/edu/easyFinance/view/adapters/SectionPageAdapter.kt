package pe.upc.edu.easyFinance.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionPageAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitle = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> mFragmentList[0]
            1 -> mFragmentList[1]
            else -> mFragmentList[2]
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> mFragmentTitle[0]
            1 -> mFragmentTitle[1]
            else -> mFragmentTitle[2]
        }
    }

    fun addFragment(fragment: Fragment, title: String){
        mFragmentList.add(fragment)
        mFragmentTitle.add(title)
    }
}
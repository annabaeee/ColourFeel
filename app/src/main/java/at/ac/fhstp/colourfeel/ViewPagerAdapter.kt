package at.ac.fhstp.colourfeel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3  // Three pages

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PageFragment1()
            1 -> PageFragment2()
            else -> PageFragment3()
        }
    }
}

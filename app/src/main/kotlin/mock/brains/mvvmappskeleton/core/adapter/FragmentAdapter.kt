package mock.brains.mvvmappskeleton.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    var items: List<Fragment>
        set(value) {
            mutableItems = value.toMutableList()
            notifyDataSetChanged()
        }
        get() = mutableItems

    private var mutableItems = mutableListOf<Fragment>()

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }

    override fun getItemCount(): Int = items.size
}
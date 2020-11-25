package mock.brains.mvvmappskeleton.core.architecture.fragment

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import org.koin.core.KoinComponent
import org.koin.core.get

open class MainNavHostFragment : NavHostFragment(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = get()
        super.onCreate(savedInstanceState)
    }
}
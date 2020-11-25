package mock.brains.mvvmappskeleton.core.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val spaceTop: Int = 0,
    private val spaceBottom: Int = 0,
    private val spaceRight: Int = 0,
    private val spaceLeft: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceTop
            }
            left = spaceLeft
            right = spaceRight
            bottom = spaceBottom
        }
    }
}
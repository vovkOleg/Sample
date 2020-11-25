package mock.brains.mvvmappskeleton.util

import android.text.method.PasswordTransformationMethod
import android.view.View

class DotPasswordTransformationMethod(private val dotSize: DotSize) : PasswordTransformationMethod() {

    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return PasswordCharSequence(super.getTransformation(source, view), dotSize)
    }

    private class PasswordCharSequence(
        private val transformation: CharSequence,
        private val dotSize: DotSize
    ) : CharSequence by transformation {
        override fun get(index: Int): Char = dotSize.dot
    }
}

enum class DotSize(val dot: Char) {
    LARGE('⬤'),
    BIG('●'),
    MEDIUM('⚫')
}
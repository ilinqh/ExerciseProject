package lqh.hchomework

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animator = ObjectAnimator.ofFloat(view, "radius", 0f, 180f, 360f, 180f, 0f)
        animator.duration = 3000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.start()
    }
}

package lqh.hchomework

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animator = ObjectAnimator.ofFloat(waveView, "mTheta", 0f, 36f)
//        animator.repeatCount = ValueAnimator.INFINITE
//        animator.repeatMode = ValueAnimator.REVERSE
//        animator.startDelay = 1000
//        animator.duration = 2000
//        animator.start()
        val heightAnimator = ObjectAnimator.ofFloat(waveView, "waveHeight", 0f, Utils.dp2px(100f))
//        heightAnimator.startDelay = 1000
//        heightAnimator.duration = 2000

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator, heightAnimator)
//        animatorSet.startDelay = 10000
        animatorSet.duration = 60000
        animatorSet.interpolator = LinearInterpolator()
//        animatorSet.start()

        button.setOnClickListener {
            animatorSet.start()
        }

        animatorSet.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                animator.cancel()
                heightAnimator.cancel()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
    }
}

package ziedkhmiri.custom_like_animated;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final TimeInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final TimeInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    ImageButton btnLike;
    TextView count,count1;
    ImageView Like;
    int mcount = 0;
    int mcount1 = 0;

    private LikeAnim mLikeAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLikeAnim = LikeAnim.attach2Window(this);
        count = (TextView)findViewById(R.id.count);
        count1 = (TextView)findViewById(R.id.count1);
        Like = (ImageView)findViewById(R.id.Like);
        Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Like.setImageResource(R.drawable.ic_heart_red);
                mLikeAnim.anim(view);
                mLikeAnim.setmListener(new LikeAnim.LikeAnimListener() {
                    @Override
                    public void onAnimationStart() {
                    }

                    @Override
                    public void onAnimationEnd() {
                        mcount++;
                        count1.setText(mcount+"  likes");
                        Like.setImageResource(R.drawable.ic_heart_outline_grey);
                    }
                });
            }
        });
        btnLike = (ImageButton)findViewById(R.id.btnLike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcount1++;
                count.setText(mcount1+"  likes");

               AnimatorSet animatorSet = new AnimatorSet();

                ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(btnLike, "rotation", 0f, 360f);
                rotationAnim.setDuration(300);
                rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

                ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(btnLike, "scaleX", 0.2f, 1f);
                bounceAnimX.setDuration(300);
                bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

                ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(btnLike, "scaleY", 0.2f, 1f);
                bounceAnimY.setDuration(300);
                bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
                bounceAnimY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        btnLike.setImageResource(R.drawable.ic_heart_red);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        btnLike.setImageResource(R.drawable.ic_heart_outline_grey);


                    }
                });

                animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);
                animatorSet.start();

            }
        });

    }


}

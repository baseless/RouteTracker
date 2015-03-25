package njp.nu.routetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class StartActivity extends ActionBarActivity {

    RouteApplication app;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_start);
        app = (RouteApplication)getApplicationContext();
        final Animation animation  = AnimationUtils.loadAnimation(this, R.anim.animation_button);
        Button button = (Button)findViewById(R.id.startButton);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                app.startRoute();
                Intent switchToRoute = new Intent(StartActivity.this, RouteActivity.class);
                startActivity(switchToRoute);
            }
        });

    }
/*
    public void onStartClick(View v) {
        final Animation animation  = AnimationUtils.loadAnimation(this, R.anim.animation_button);
        v.startAnimation(animation);
        app.startRoute();
        Intent switchToRoute = new Intent(StartActivity.this, RouteActivity.class);
        startActivity(switchToRoute);
    }
    */
}

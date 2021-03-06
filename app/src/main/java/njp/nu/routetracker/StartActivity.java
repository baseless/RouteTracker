package njp.nu.routetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class StartActivity extends ActionBarActivity {

    RouteApplication app;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_start);
        app = (RouteApplication)getApplicationContext();
    }

    public void onStartClick(View v) {
        app.startRoute();
        Intent switchToRoute = new Intent(StartActivity.this, RouteActivity.class);
        startActivity(switchToRoute);
    }
}

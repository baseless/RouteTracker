package njp.nu.routetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import njp.nu.routetracker.services.StatisticsService;

public class ResultActivity extends ActionBarActivity {

    RouteApplication app;

    @Override
    protected  void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_result);
        app = (RouteApplication)getApplicationContext();
        setStatistics();
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_button);
        Button button = (Button)findViewById(R.id.startANewRoute);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                Intent switchToRoute = new Intent(ResultActivity.this, RouteActivity.class);
                app.startRoute();
                startActivity(switchToRoute);
            }
        });
    }

    private void setStatistics() {
        RouteApplication app = (RouteApplication)getApplicationContext();
        StatisticsService stats = new StatisticsService(app.getRouteLocations());

        TextView avgSpeed = (TextView)findViewById(R.id.viewAvgSpeed);
        TextView distance = (TextView)findViewById(R.id.viewDist);
        TextView time = (TextView)findViewById(R.id.viewTime);

        float dist = app.getRouteDistance();
        time.setText("Time to complete the route: " + stats.getElapsedTime());
        avgSpeed.setText("Average speed: " + String.format("%.2f",stats.getAverageSpeed(dist)) + " m/s");
        distance.setText("Time traveled: " + String.format("%.2f",dist) + " meters");
    }
}

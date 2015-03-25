package njp.nu.routetracker;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class RouteWidget extends AppWidgetProvider {

    public final static String WIDGET_START_CLICK = "njp.nu.routeTracker.WIDGET_START_CLICK";
    public final static String WIDGET_STOP_CLICK = "njp.nu.routeTracker.WIDGET_STOP_CLICK";
    public final static String WIDGET_ICON_CLICK = "njp.nu.routeTracker.WIDGET_ICON_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.route_widget);
        ComponentName watchWidget = new ComponentName(context, RouteWidget.class);

        RouteApplication app = (RouteApplication)context.getApplicationContext();
        if(app.isStarted()) {
            Uri imgStarted = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/icon_njp_act");
            remoteViews.setImageViewUri(R.id.widgetImage, imgStarted);
        } else {
            Uri imgStopped = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/icon_njp");
            remoteViews.setImageViewUri(R.id.widgetImage, imgStopped);
        }

        remoteViews.setOnClickPendingIntent(R.id.startWidgetButton, getPendingIntent(context, WIDGET_START_CLICK));
        remoteViews.setOnClickPendingIntent(R.id.stopWidgetButton, getPendingIntent(context, WIDGET_STOP_CLICK));
        remoteViews.setOnClickPendingIntent(R.id.stopWidgetButton, getPendingIntent(context, WIDGET_ICON_CLICK));
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.route_widget);
        ComponentName watchWidget = new ComponentName(context, RouteWidget.class);
        RouteApplication app = (RouteApplication)context.getApplicationContext();

        if (WIDGET_START_CLICK.equals(intent.getAction())) {
            app.startRoute();
            Uri imgStarted = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/icon_njp_act");
            remoteViews.setImageViewUri(R.id.widgetImage, imgStarted);
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        } else if(WIDGET_STOP_CLICK.equals(intent.getAction())) {
            app.stopRoute();
            Uri imgStopped = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/icon_njp");
            remoteViews.setImageViewUri(R.id.widgetImage, imgStopped);
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        }else if(WIDGET_ICON_CLICK.equals(intent.getAction())) {
            if(app.isStarted()) {

            } else {

            }
        }
    }

    protected PendingIntent getPendingIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}



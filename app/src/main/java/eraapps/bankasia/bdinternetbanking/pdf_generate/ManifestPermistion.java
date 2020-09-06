package eraapps.bankasia.bdinternetbanking.pdf_generate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ManifestPermistion {



    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    public static boolean hasCheckPermsion(Context context, String permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            ///for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permissions) != PackageManager.PERMISSION_GRANTED) {
                return false;
                //}
            }
        }
        return true;
    }

    public static boolean checkSystemWritePermission(Context context) {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(context);
            Log.e("Can Not wridte", "Can Write Settings: " + retVal);
            if(retVal){
                //Toast.makeText(context, "Write allowed :-)", Toast.LENGTH_LONG).show();
            }else{
                //Toast.makeText(context, "Write not allowed :-(", Toast.LENGTH_LONG).show();

            }
        }
        return retVal;
    }


    public static void GoToSettingPermission(final Activity activity, String permission, String permissionName){
        // ContextCompat.checkSelfPermission()
        //Manifest.permission.READ_PHONE_STATE
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            // show a dialog
            //You need to enable permissions to use this feature
            new AlertDialog.Builder(activity).setMessage("You need to enable "+permissionName+" permission to use Bank Asia SMART App").setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // navigate to settings
                    activity.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:eraapps.bankasia.bdinternetbanking.apps")));
                    // startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            }).setNegativeButton("Go back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // leave?
                    activity.onBackPressed();
                }
            }).show();
        }
    }

}


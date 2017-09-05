package org.amoustakos.boilerplate.util.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import org.amoustakos.boilerplate.injection.annotations.context.ActivityContext;


/**
 * TODO: WiP
 */
public final class DialogFactory {




    public static Dialog createSimpleOkErrorDialog(
										            @ActivityContext Context context,
										            String title,
										            String message,
                                                    String neutralButtonStr
                                                ) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(neutralButtonStr, null);
        return alertDialog.create();
    }





    public static Dialog createSimpleOkErrorDialog(
													@ActivityContext Context context,
													@StringRes int titleResource,
													@StringRes int messageResource,
													@StringRes int neutralButtonStr
                                                  ) {
        return createSimpleOkErrorDialog(
        		context,
                context.getString(titleResource),
                context.getString(messageResource),
		        context.getString(neutralButtonStr)
        );
    }



    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        return progressDialog;
    }




    public static ProgressDialog createProgressDialog(Context context, @StringRes int messageResource) {
        return createProgressDialog(context, context.getString(messageResource));
    }





}

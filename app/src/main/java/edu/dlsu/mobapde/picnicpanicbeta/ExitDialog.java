package edu.dlsu.mobapde.picnicpanicbeta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Allyza on 15/12/2017.
 */

public class ExitDialog extends DialogFragment {

    Button buttonExit;
    Button buttonCancel;
    TextView tvExit;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.exit_layout, null);

        buttonExit = (Button) v.findViewById(R.id.button_exit_exit);
        buttonCancel = (Button) v.findViewById(R.id.button_exit_cancel);
        tvExit = (TextView) v.findViewById(R.id.tv_exit);

        Typeface buttonTypeface = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/unica_one.ttf");
        Typeface typeface = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/dancing_script.ttf");

        buttonExit.setTypeface(buttonTypeface);
        buttonCancel.setTypeface(buttonTypeface);
        tvExit.setTypeface(buttonTypeface);

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity())
                .setView(v);

        return builder.create();
    }
}

package edu.dlsu.mobapde.picnicpanicbeta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by acer on 05/12/2017.
 */

public class PauseDialog extends DialogFragment {
    Button buttonResume;
    Button buttonRestart;
    Button buttonOptions;
    Button buttonHome;
    TextView tvPause;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.pause_layout, null);
        buttonResume = (Button) v.findViewById(R.id.button_resume);
        buttonRestart = (Button) v.findViewById(R.id.button_restart);
        buttonOptions = (Button) v.findViewById(R.id.button_options);
        buttonHome = (Button) v.findViewById(R.id.button_home);
        tvPause = (TextView) v.findViewById(R.id.tv_pause);

        Typeface buttonTypeface = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/unica_one.ttf");
        Typeface typeface = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/dancing_script.ttf");

        buttonResume.setTypeface(buttonTypeface);
        buttonRestart.setTypeface(buttonTypeface);
        buttonOptions.setTypeface(buttonTypeface);
        buttonHome.setTypeface(buttonTypeface);
        tvPause.setTypeface(typeface);

        buttonResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityGame)getActivity()).resume();
                dismiss();
            }
        });

        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(v.getContext(), ActivityGame.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intents);
                dismiss();
            }
        });

        buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // music on off
                // sound on off
            }
        });

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go home
                Intent intents = new Intent(v.getContext(), MainActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intents);
                dismiss();
            }
        });

        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity())
                .setView(v);
        return builder.create();
    }
}

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by acer on 05/12/2017.
 */

public class PauseDialog extends DialogFragment {
    Button buttonResume;
    Button buttonRestart;
    ImageView buttonMusic;
    ImageView buttonSounds;
    Button buttonHome;
    TextView tvPause;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.pause_layout, null);

        buttonResume = (Button) v.findViewById(R.id.button_resume);
        buttonRestart = (Button) v.findViewById(R.id.button_restart);
        buttonHome = (Button) v.findViewById(R.id.button_home);
        buttonMusic = (ImageView) v.findViewById(R.id.button_music);
        buttonMusic.setTag(R.drawable.music);
        buttonSounds = (ImageView) v.findViewById(R.id.button_sounds);
        buttonSounds.setTag(R.drawable.sounds);
        tvPause = (TextView) v.findViewById(R.id.tv_pause);

        Typeface buttonTypeface = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/unica_one.ttf");
        Typeface typeface = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/dancing_script.ttf");

        buttonResume.setTypeface(buttonTypeface);
        buttonRestart.setTypeface(buttonTypeface);
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

        buttonMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonSounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Integer)buttonSounds.getTag() == R.drawable.sounds) {
                    buttonSounds.setTag(R.drawable.sounds_no);
                    buttonSounds.setImageResource(R.drawable.sounds_no);
                } else {
                    buttonSounds.setTag(R.drawable.sounds);
                    buttonSounds.setImageResource(R.drawable.sounds);
                }

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
        Dialog d =  builder.create();
        d.setCanceledOnTouchOutside(false);

        return d;
    }

}

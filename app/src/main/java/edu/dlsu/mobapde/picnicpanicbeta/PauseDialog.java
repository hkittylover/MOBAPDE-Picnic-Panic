package edu.dlsu.mobapde.picnicpanicbeta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.BoolRes;
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
    Boolean sounds;
    Boolean music;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.pause_layout, null);

        buttonResume = (Button) v.findViewById(R.id.button_resume);
        buttonRestart = (Button) v.findViewById(R.id.button_restart);
        buttonHome = (Button) v.findViewById(R.id.button_home);
        buttonMusic = (ImageView) v.findViewById(R.id.button_music);
        if (((ActivityGame)getActivity()).getMusic()) {
            buttonMusic.setImageResource(R.drawable.music);
            buttonMusic.setTag(R.drawable.music);
            music = true;
        } else {
            buttonMusic.setTag(R.drawable.music_no);
            buttonMusic.setImageResource(R.drawable.music_no);
            music = false;
        }

        buttonSounds = (ImageView) v.findViewById(R.id.button_sounds);
        if (((ActivityGame)getActivity()).getSounds()) {
            buttonSounds.setImageResource(R.drawable.sounds);
            buttonSounds.setTag(R.drawable.sounds);
            sounds = true;
        } else {
            buttonSounds.setImageResource(R.drawable.sounds_no);
            buttonSounds.setTag(R.drawable.sounds_no);
            sounds = false;
        }

        tvPause = (TextView) v.findViewById(R.id.tv_pause);

        Typeface buttonTypeface = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/unica_one.ttf");
        Typeface typeface = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/dancing_script.ttf");

        buttonResume.setTypeface(buttonTypeface);
        buttonRestart.setTypeface(buttonTypeface);
        buttonHome.setTypeface(buttonTypeface);
        tvPause.setTypeface(buttonTypeface);

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
                ((ActivityGame)getActivity()).stopMusic();
                dismiss();
            }
        });

        buttonMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Integer)buttonMusic.getTag() == R.drawable.music) {
                    buttonMusic.setTag(R.drawable.music_no);
                    buttonMusic.setImageResource(R.drawable.music_no);
                    ((ActivityGame)getActivity()).pauseMusic();
                } else {
                    buttonMusic.setTag(R.drawable.music);
                    buttonMusic.setImageResource(R.drawable.music);
                    music = true;
                    ((ActivityGame)getActivity()).resumeMusic();
                }

            }
        });

        buttonSounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Integer)buttonSounds.getTag() == R.drawable.sounds) {
                    buttonSounds.setTag(R.drawable.sounds_no);
                    buttonSounds.setImageResource(R.drawable.sounds_no);
                    sounds = false;
                } else {
                    buttonSounds.setTag(R.drawable.sounds);
                    buttonSounds.setImageResource(R.drawable.sounds);
                    sounds = true;
                }
                ((ActivityGame)getActivity()).setSounds(sounds);
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
                ((ActivityGame)getActivity()).stopMusic();
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

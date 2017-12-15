package edu.dlsu.mobapde.picnicpanicbeta;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.IntentCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SaveMeDialog extends DialogFragment {
    public static int COIN_NEED = 40;
    Button buttonYes, buttonNo;
    TextView tvWallet, tvCoinTotal, tvSaveMe, tvCoinNeed;
    int coinTotal, score;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.activity_save_me, null);

        buttonYes = (Button) v.findViewById(R.id.button_yes);
        buttonNo = (Button) v.findViewById(R.id.button_no);
        tvWallet = (TextView) v.findViewById(R.id.tv_wallet);
        tvCoinTotal = (TextView) v.findViewById(R.id.tv_coin_total);
        tvSaveMe = (TextView) v.findViewById(R.id.tv_save_me);
        tvCoinNeed = (TextView) v.findViewById(R.id.tv_coin_need);

        Typeface typeface = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/unica_one.ttf");

        buttonYes.setTypeface(typeface);
        buttonNo.setTypeface(typeface);
        tvWallet.setTypeface(typeface);
        tvSaveMe.setTypeface(typeface);
        tvCoinNeed.setTypeface(typeface);
        tvCoinTotal.setTypeface(typeface);

        coinTotal = getArguments().getInt("coinTotal");
        score = getArguments().getInt("score");

        tvCoinNeed.setText("" + COIN_NEED);
        tvCoinTotal.setText("" + coinTotal);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityGame)getActivity()).saved();
                dismiss();
            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(v.getContext(), GameOverActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                intents.putExtra("score", score);
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

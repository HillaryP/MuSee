package edu.uw.prathh.musee.media;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import edu.uw.prathh.musee.MuSeeApp;

public class MediaPauseDialogFragment extends DialogFragment {
    String s;

    public void setMediaPlayer(String s) {
        this.s = s;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Currently playing \"" + s + "\"")
                .setPositiveButton("Stop", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MuSeeApp) getActivity().getApplication()).mediaPlayer.stop();
                        ((MuSeeApp) getActivity().getApplication()).mediaPlayer.reset();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
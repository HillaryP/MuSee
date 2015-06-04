package edu.uw.prathh.musee;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by owong on 6/3/15.
 */
public class ScreenSlidePageFragment extends Fragment{
    private int position;

    public ScreenSlidePageFragment() {
    }

    public static ScreenSlidePageFragment newInstance() {
        ScreenSlidePageFragment f = new ScreenSlidePageFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.position = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.tutorial_1, container, false);
        switch (position) {
            case 0:
                rootView = (ViewGroup) inflater.inflate(R.layout.tutorial_1, container, false);
                break;
            case 1:
                rootView = (ViewGroup) inflater.inflate(R.layout.tutorial_2, container, false);
                break;
            case 2:
                rootView = (ViewGroup) inflater.inflate(R.layout.tutorial_3, container, false);
                break;
            case 3:
                rootView = (ViewGroup) inflater.inflate(R.layout.tutorial_4, container, false);
                break;
            case 4:
                rootView = (ViewGroup) inflater.inflate(R.layout.tutorial_5, container, false);
                break;
        }
        return rootView;
    }
}

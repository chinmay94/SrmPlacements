package edroids.srmplacements.MainUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edroids.srmplacements.R;

/**
 * Created by Chinmay on 05-Apr-15.
 */
public class FragmentPersonal extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_personal,container,false);
        return rootview;
    }
}

package edroids.srmplacements.MainUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edroids.srmplacements.R;

/**
 * Created by Chinmay on 05-Apr-15.
 */
public class FragmentHome extends Fragment {
    TextView t3;
    TextView t4;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_home,container,false);
        t3=(TextView)rootview.findViewById(R.id.textView3);
        t4=(TextView)rootview.findViewById(R.id.textView4);
        t3.setMovementMethod(new ScrollingMovementMethod());
        t4.setMovementMethod(new ScrollingMovementMethod());
        return rootview;
    }
}
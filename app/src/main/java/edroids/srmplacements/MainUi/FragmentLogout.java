package edroids.srmplacements.MainUi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edroids.srmplacements.LoginAndRegister.LoginActivity;
import edroids.srmplacements.R;

/**
 * Created by Chinmay on 27-Apr-15.
 */
public class FragmentLogout extends Fragment {

    Button b;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_logout,container,false);
        b=(Button)rootview.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSharedPreferences("CoreData",0).edit().clear().commit();
                Intent i=new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return rootview;
    }
}

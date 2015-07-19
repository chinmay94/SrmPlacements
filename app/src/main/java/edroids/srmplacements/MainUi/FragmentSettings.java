package edroids.srmplacements.MainUi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import edroids.srmplacements.LoginAndRegister.LoginTask;
import edroids.srmplacements.R;
import edroids.srmplacements.otherAsyncTasks.CpTask;

/**
 * Created by Chinmay on 05-Apr-15.
 */
public class FragmentSettings extends Fragment {

    EditText cp;
    EditText np1;
    EditText np2;
    Button b;
    ProgressDialog Dialog;

    AsyncTask<String, Void, String> task;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_cp2,container,false);
        cp=(EditText)rootview.findViewById(R.id.cp);
        np1=(EditText)rootview.findViewById(R.id.np1);
        np2=(EditText)rootview.findViewById(R.id.np2);
        b=(Button)rootview.findViewById(R.id.button);
        Dialog=new ProgressDialog(getActivity());
        final SharedPreferences prefs=getActivity().getSharedPreferences("CoreData", Context.MODE_PRIVATE);
        final String regno=prefs.getString("RegistrationNumber", null);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(np1.getText().toString().equals(np2.getText().toString())){
                    task = new CpTask(getActivity(),Dialog,regno,cp.getText().toString(),np1.getText().toString()).execute();
                }
            }
        });
        return rootview;
    }
}

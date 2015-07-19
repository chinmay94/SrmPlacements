package edroids.srmplacements.History;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edroids.srmplacements.R;

/**
 * Created by Chinmay on 05-May-15.
 */
public class History extends Fragment {

    View rootview;

    RecyclerView recList;
    LinearLayoutManager llm;
    ContactAdapter ca;
    TextView msg;

    ProgressDialog Dialog;
    SharedPreferences prefs;
    String regno;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_history2,container,false);

        recList = (RecyclerView) rootview.findViewById(R.id.cardList);
        msg=(TextView)rootview.findViewById(R.id.msg);
        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        prefs=getActivity().getSharedPreferences("CoreData", Context.MODE_PRIVATE);
        regno=prefs.getString("RegistrationNumber", null);
        Dialog =new ProgressDialog(getActivity());

        AsyncTask<String, Void, String> atask = new awesomeHistory(getActivity(),Dialog,regno,recList,msg).execute();

        return rootview;
    }
}

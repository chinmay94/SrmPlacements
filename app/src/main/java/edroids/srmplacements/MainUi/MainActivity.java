package edroids.srmplacements.MainUi;


import android.content.SharedPreferences;
import android.os.Bundle;

import edroids.srmplacements.History.History;
import edroids.srmplacements.R;
import edroids.srmplacements.Reply.Reply;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by Chinmay on 05-Apr-15.
 */
public class MainActivity  extends MaterialNavigationDrawer {
    @Override
    public void init(Bundle savedInstanceState) {

        // create and set the header
        setDrawerHeaderImage(R.drawable.header);

        SharedPreferences prefs = getSharedPreferences("StudentData", MODE_PRIVATE);
        String name=prefs.getString("StudentName","name");
        String regid=prefs.getString("RegistrationNumber","id");

        setUsername(name);
        setUsernameTextColor(getResources().getColor(R.color.xcolor));
        setUserEmail(regid);
        setUserEmailTextColor(getResources().getColor(R.color.xcolor));

        // create sections
        this.addSection(newSection("Home",R.drawable.icon_home,new FragmentHome()));
        //this.addSection(newSection("Reply",R.drawable.icon_reply,new FragmentReply()));
        this.addSection(newSection("Reply",R.drawable.icon_reply,new Reply()));
        this.addSection(newSection("History", R.drawable.icon_history, new History()));
        this.addSection(newSection("Personal Details",R.drawable.icon_personal,new FragmentPersonal()));
        this.addSection(newSection("Change Password",R.drawable.icon_settings,new FragmentSettings()));
        this.addSection(newSection("Logout",R.drawable.icon_logout,new FragmentLogout()));
        this.addBottomSection(newSection("About",R.drawable.icon_about,new FragmentAbout()));

        this.closeDrawer();
    }
}
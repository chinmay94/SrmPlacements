package edroids.srmplacements.Reply;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edroids.srmplacements.R;

//import android.widget.AdapterView;

/**
 * Created by Chinmay on 05-May-15.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactInfo> contactList;
    Context context;
    int lastPosition=-1;

    public ContactAdapter(List<ContactInfo> contactList,Context context)
    {
        this.contactList = contactList;
        this.context=context;
    }


    @Override
    public int getItemCount()
    {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ContactInfo ci = contactList.get(i);
        contactViewHolder.company.setText(ci.company);
        contactViewHolder.content.setText(ci.content);
        contactViewHolder.date.setText(ci.date);
        contactViewHolder.time.setText(ci.time);
        if(ci.reply.equalsIgnoreCase("-"))
            contactViewHolder.status.setImageDrawable(context.getResources().getDrawable(R.drawable.cross));
        else
            contactViewHolder.status.setImageDrawable(context.getResources().getDrawable(R.drawable.tick));
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_item, viewGroup, false);
        return new ContactViewHolder(itemView);
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder
    {
        protected TextView company;
        protected TextView content;
        protected TextView date;
        protected TextView time;
        protected ImageView status;
        public ContactViewHolder(View v)
        {
            super(v);
            company =  (TextView) v.findViewById(R.id.company);
            content = (TextView)  v.findViewById(R.id.content);
            date = (TextView)  v.findViewById(R.id.date);
            time = (TextView) v.findViewById(R.id.time);
            status=(ImageView)v.findViewById(R.id.statusimg);
        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            //Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.push_up_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
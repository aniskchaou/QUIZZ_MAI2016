package com.tmm.android.quizzGlid.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.tmm.android.quizzGlid.MainActivity;
import com.tmm.android.quizzGlid.R;
import com.tmm.android.quizzGlid.quiz.Constants;

public class LanguagesFragment extends Fragment implements OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner sp_langue;
    Button btn_retour;
    Switch vibreur;
    SeekBar ajuster_volume;
    public LanguagesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.languages, null);
        btn_retour = (Button) v.findViewById(R.id.bt_retour);
        btn_retour.setOnClickListener(this);
        Spinner langue = (Spinner) v.findViewById(R.id.spinner);
        vibreur= (Switch) v.findViewById(R.id.st_vib);




// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.langue_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        langue.setAdapter(adapter);
        langue.setOnItemSelectedListener(this);
        vibreur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    Vibrator vd = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    vd.vibrate(500);
                    show_notification("vibration est activé");
                }else
                {
                    show_notification("vibration est désactivé");
                }
            }
        });
        return v;
    }



    @Override
    public void onClick(View v) {


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences settings = getActivity().getSharedPreferences(Constants.SETTINGS, 0);
        Editor e = settings.edit();
        //the images to display
        Integer[] imageIDs = {R.drawable.ic_france,
                R.drawable.ic_espagne,
                R.drawable.ic_usa
        };
        ImageView drapeau = (ImageView) view.findViewById(R.id.iv_drapeau);
        String selectedItem = parent.getItemAtPosition(position).toString();
        if(selectedItem.equals("Français"))
        {
            // drapeau.setImageResource(imageIDs[0]);
            e.putInt(Constants.LANGUAGES, Constants.FR);
        }
        else if(selectedItem.equals("Anglais")){
            //drapeau.setImageResource(imageIDs[2]);
            e.putInt(Constants.LANGUAGES, Constants.EN);
        }
        else if(selectedItem.equals("Espagnol")){
            // drapeau.setImageResource(imageIDs[1]);
            e.putInt(Constants.LANGUAGES, Constants.SP);
        }
        e.commit();
        // ((MainActivity)getActivity()).goBack();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void  show_notification(String title)
    {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int icon = R.drawable.ic_drawer;
        NotificationCompat.Builder b = new NotificationCompat.Builder(getActivity());

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(icon)
                .setTicker(title)
                .setContentTitle(title)

                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }
}

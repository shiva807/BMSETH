package com.example.bmseth;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentProfile extends Fragment {

    //private onFragmentbtnSelected listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_profile, container, false);
        ///Button bookroom=view1.findViewById(R.id.button3);
      /*  bookroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected();
            }
        });*/
        return view1;
    }
}

  /*  @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentbtnSelected) {
            listener = (onFragmentbtnSelected) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement listener");

        }

    }

    public interface onFragmentbtnSelected{
        public void onButtonSelected();

    }
}*/

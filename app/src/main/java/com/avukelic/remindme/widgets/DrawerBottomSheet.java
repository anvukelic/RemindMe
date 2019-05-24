package com.avukelic.remindme.widgets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.avukelic.remindme.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerBottomSheet extends BottomSheetDialogFragment {

    DrawerBottomSheetActionCallback callback;

    //region ButterKnife
    @BindView(R.id.nv_drawer)
    NavigationView navigationView;
    //endregon

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_drawer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                // TODO: 24.5.2019. finish this
            }
            return false;
        });
    }

    public void setCallback(DrawerBottomSheetActionCallback callback) {
        this.callback = callback;
    }

    public interface DrawerBottomSheetActionCallback {

    }
}

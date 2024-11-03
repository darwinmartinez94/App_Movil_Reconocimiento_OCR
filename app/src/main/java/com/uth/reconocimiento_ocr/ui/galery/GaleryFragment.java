package com.uth.reconocimiento_ocr.ui.galery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.uth.reconocimiento_ocr.databinding.FragmentGaleryBinding;

public class GaleryFragment extends Fragment {

    private FragmentGaleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GaleryViewModel galeryViewModel =
                new ViewModelProvider(this).get(GaleryViewModel.class);

        binding = FragmentGaleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textNotifications;
        //galeryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.uth.reconocimiento_ocr.ui.galery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GaleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GaleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
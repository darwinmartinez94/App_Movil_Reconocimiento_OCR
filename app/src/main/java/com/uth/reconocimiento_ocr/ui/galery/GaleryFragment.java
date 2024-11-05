package com.uth.reconocimiento_ocr.ui.galery;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.uth.reconocimiento_ocr.databinding.FragmentGaleryBinding;

import java.io.InputStream;

public class GaleryFragment extends Fragment {

    private FragmentGaleryBinding binding;
    //variable para buscar la imagen desde drawable
    private Bitmap imagenSeleccionada;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GaleryViewModel galeryViewModel =
                new ViewModelProvider(this).get(GaleryViewModel.class);

        binding = FragmentGaleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //llamar a los metodos para el OCR
        imagenSeleccionada = obtenerImagendeAsset(this.getContext(), "identidad.png");


        return root;
    }

    //METODO PARA EJECUTRAR EL RECONOCEDOR DE OCR
    private void ejecutarReconocedorOCR(){
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        InputImage imageEnPantall = InputImage.fromBitmap(imagenSeleccionada, 0);

    }

    //Metdo para buscar una imagen en mi carpeta drawable
    private static Bitmap obtenerImagendeAsset(Context contexto, String nombreArchivo){
        AssetManager assetManager = contexto.getAssets();
        InputStream is;
        Bitmap bitmap = null;
        try {
            is = assetManager.open(nombreArchivo);
            bitmap = BitmapFactory.decodeStream(is);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
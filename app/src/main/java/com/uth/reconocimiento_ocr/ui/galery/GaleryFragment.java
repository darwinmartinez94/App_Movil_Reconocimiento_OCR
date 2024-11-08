package com.uth.reconocimiento_ocr.ui.galery;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.uth.reconocimiento_ocr.R;
import com.uth.reconocimiento_ocr.databinding.FragmentGaleryBinding;

import java.io.InputStream;
import java.util.List;

public class GaleryFragment extends Fragment {

    private FragmentGaleryBinding binding;
    private ImageView img_Seleccionada;
    //variable para buscar la imagen desde drawable
    private Bitmap imagenSeleccionada;
    private Integer maxImageWidth;
    private Integer maxImageHeigth;
    private Button btnProcesar, btn_mostrarImagen;
    TextView txtResultado;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GaleryViewModel galeryViewModel =
                new ViewModelProvider(this).get(GaleryViewModel.class);

        binding = FragmentGaleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //traer los botones y imageView del layout
        img_Seleccionada = binding.imgSeleccionada;
        btn_mostrarImagen = binding.btnSeleccionarImagen;
        btnProcesar = binding.btnProcesar;
        txtResultado = binding.txtresult;


        //llamar al metodo obtenerImagendeAsset para cargar la img de drawable
       imagenSeleccionada = obtenerImagendeAsset(this.getContext(), "img_texto.png");

       //controlar el boton de seleccionar una imagen
        View.OnClickListener btnClick = view -> mostrarImagen();
        btn_mostrarImagen.setOnClickListener(btnClick);

        //boton para procesar el texto de la imagen seleccionada
        btnProcesar.setOnClickListener(view -> ejecutarReconocedorOCR());

        return root;
    }

    //mostrar imagen de la galeria
    private void mostrarImagen(){
        if(imagenSeleccionada != null){
            //obtener el tamaño maximo de la imagen para ponerla completa en mi layout
            Pair<Integer, Integer> targetedSize = this.getTargetedWidthHeight();

            int targetedWidth = targetedSize.first;
            int targetedHeight = targetedSize.second;

            float scaleFactor = Math.max((float) imagenSeleccionada.getWidth() / (float) targetedWidth,
                                            (float) imagenSeleccionada.getHeight()/(float) targetedHeight);

            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imagenSeleccionada,
                    (int) (imagenSeleccionada.getWidth()/scaleFactor),
                    (int)(imagenSeleccionada.getHeight()/scaleFactor), true);

            img_Seleccionada.setImageBitmap(resizedBitmap);
            imagenSeleccionada = resizedBitmap;
        }
        //mostrar el boton oculta hasta que se cargue una imagen
        btnProcesar.setEnabled(imagenSeleccionada != null);
    }

    //metodo para hacer que la imagen se acople al tamaño del layout mi pantalla
    private Pair<Integer, Integer> getTargetedWidthHeight(){
        int targetWidth;
        int targetHeight;
        int maxWidthForPortraitMode = getImageMaxWidth();
        int maxHeigthForPortraitMode = getImageMaxHeight();
        targetWidth = maxWidthForPortraitMode;
        targetHeight = maxHeigthForPortraitMode;

        return new Pair<>(targetWidth, targetHeight);
    }

    private int getImageMaxHeight() {
        if(maxImageHeigth == null){
            maxImageHeigth = img_Seleccionada.getHeight();
        }
        return maxImageHeigth;
    }

    private int getImageMaxWidth() {
        if(maxImageWidth == null){
            maxImageWidth = img_Seleccionada.getWidth();
        }
        return maxImageWidth;
    }

    //METODO PARA EJECUTRAR EL RECONOCEDOR DE OCR
    private void ejecutarReconocedorOCR(){
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        InputImage imageEnPantall = InputImage.fromBitmap(imagenSeleccionada, 0);
        
        recognizer.process(imageEnPantall)
                .addOnSuccessListener(this::processTextRecognitionResult)
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });

    }

    private void processTextRecognitionResult(Text text) {
        List<Text.TextBlock> bloques = text.getTextBlocks();
        if(bloques.size() == 0){
            showToast("No se encontro texto en la imagen");
            return;
        }
        StringBuilder textos = new StringBuilder();
        for (int i=0; i<bloques.size(); i++){
            List<Text.Line> lineas = bloques.get(i).getLines();
            textos.append(lineas.get(0).getText()).append("\n");
            for(int l=0; l<lineas.size(); l++){
                List<Text.Line> elementos = bloques.get(l).getLines();
                for(int e=0; e<elementos.size(); e++){

                }
            }
        }
        txtResultado.setText(textos.toString());
    }

    private void showToast(String mensage) {
        Toast.makeText(this.getContext(), mensage, Toast.LENGTH_SHORT).show();
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
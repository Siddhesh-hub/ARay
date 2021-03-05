package com.SidStudio.ARay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.core.AugmentedFace;
import com.google.ar.core.Frame;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.AugmentedFaceNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static android.widget.Toast.*;

public class ARscreen extends AppCompatActivity {

    RelativeLayout progressBar;
    Button downloadBtn;

    private ModelRenderable modelRenderable;
    private Texture mTexture;
    private boolean isAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_a_rscreen);

        //progressBar = findViewById(R.id.fragment_progress_bar);
        //downloadBtn = findViewById(R.id.modelDownloadBtn);

        FirebaseApp.initializeApp(this);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference modelRef = storageReference.child("Glass_3D_models/Cat_Reading_Glass1.sfb");

        long MAXBYTES = 1024*1024;



        CustomArFragment customArFragment = (CustomArFragment) getSupportFragmentManager()
                .findFragmentById(R.id.arFragment);

//        File mFile = null;
//        try {
//            mFile = File.createTempFile("model","glb");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        RenderableSource renderableSource = RenderableSource
//                .builder()
//                .setSource(this,
//                        Uri.parse(file.getPath()), RenderableSource.SourceType.GLB)
//                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
//                .build();
//
//        ModelRenderable
//                .builder()
//                .setSource(this, )
//                .build()
//                .thenAccept(renderable -> {
//                    modelRenderable = renderable;
//                    modelRenderable.setShadowReceiver(false);
//                    modelRenderable.setShadowCaster(false);
//                });

        /*Texture
                .builder()
                .setSource(this, R.raw.temp_glasses)
                .build()
                .thenAccept(texture -> {
                    mTexture = texture;
                });*/

        customArFragment.getArSceneView().setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);

        customArFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {

            if(modelRenderable == null) {
                makeText(this, "Renderable null", LENGTH_SHORT).show();
                return;
            }
//
//            if(mTexture == null){
//                Toast.makeText(this, "Texture null", Toast.LENGTH_SHORT).show();
//                return;
//            }

            Frame frame = customArFragment.getArSceneView().getArFrame();
            Collection<AugmentedFace> augmentedFaces = frame.getUpdatedTrackables(AugmentedFace.class);
            for (AugmentedFace augmentedFace : augmentedFaces){
                if (isAdded){
                    return;
                }
                AugmentedFaceNode augmentedFaceNode = new AugmentedFaceNode(augmentedFace);
                augmentedFaceNode.setParent(customArFragment.getArSceneView().getScene());
                augmentedFaceNode.setFaceRegionsRenderable(modelRenderable);
                augmentedFaceNode.setFaceMeshTexture(mTexture);
                isAdded = true;

            }
        });

//        modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                buildModel(finalFile);
//            }
//        });

//        findViewById(R.id.modelDownloadBtn)
//                .setOnClickListener(v -> {
//                    progressBar.setVisibility(View.VISIBLE);
//
//                    try {
//                        File file = File.createTempFile("model", "glb");
//                        modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                buildModel(file);
//                            }
//                        });
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                });







//        customArFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
//            AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
//            anchorNode.setRenderable(renderable);
//            customArFragment.getArSceneView().getScene().addChild(anchorNode);
//        });
    }

    private void buildModel(File file) {
        makeText(this, "Building renderable", LENGTH_SHORT).show();
        RenderableSource renderableSource = RenderableSource
                .builder()
                .setSource(this,
                        Uri.parse(file.getPath()), RenderableSource.SourceType.GLB)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build();

        makeText(this, "renderable built", LENGTH_SHORT).show();
        ModelRenderable
                .builder()
                .setSource(this, renderableSource)
                .setRegistryId(file.getPath())
                .build()
                .thenAccept(modelRenderable ->{
                    makeText(this, "Model built", LENGTH_SHORT).show();
                    this.modelRenderable = modelRenderable;
                    this.modelRenderable.setShadowCaster(false);
                    this.modelRenderable.setShadowReceiver(false);
                });

        //Build texture
        Texture
                .builder()
                .setSource(renderableSource)
                .setRegistryId(file.getPath())
                .build()
                .thenAccept(texture -> {
                    makeText(this, "Texture built", LENGTH_SHORT).show();
                    this.mTexture = texture;

                });

        downloadBtn.setText("Downloaded");
        progressBar.setVisibility(View.GONE);

    }
}
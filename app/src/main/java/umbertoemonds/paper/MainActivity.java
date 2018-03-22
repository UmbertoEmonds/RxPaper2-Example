package umbertoemonds.paper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pacoworks.rxpaper2.RxPaperBook;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import umbertoemonds.paper.data.produit.model.Produit;
import umbertoemonds.paper.data.rayon.RayonManager;
import umbertoemonds.paper.data.rayon.model.Rayon;

public class MainActivity extends AppCompatActivity {

    private Button mReadBtn;
    private Button mWriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReadBtn = findViewById(R.id.read);
        mWriteBtn = findViewById(R.id.write);

        // Initialisation d'RxPaper
        RxPaperBook.init(getApplicationContext());

        mWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Produit> produits = new ArrayList<>();

                produits.add(new Produit(1, "Red Bull", 1.20F));
                produits.add(new Produit(2, "Monster Energy", 1.30F));

                // Le rayon (contenant des produits) que nous voulons persister
                Rayon monRayon = new Rayon("Boissins", produits);

                // Ecriture (sur un nouveau thread) de notre rayon
                RayonManager.INSTANCE.writeRayon("monRayon", monRayon).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post("Rayon sauvegardé avec succès !");
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                });

            }
        });

        mReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Tentative de récupération d'un rayon dans la base de donnée avec la clé "monRayon"
                RayonManager.INSTANCE.getRayon("monRayon").subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Rayon>() {

                    @Override
                    public void onSuccess(Rayon rayon) {
                        EventBus.getDefault().post(rayon);
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                });

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRayonReaded(Rayon rayon) {

        Toast.makeText(getApplicationContext(), "Rayon \"" + rayon.getName() + "\" lu avec succès", Toast.LENGTH_LONG).show();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRayonWrited(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
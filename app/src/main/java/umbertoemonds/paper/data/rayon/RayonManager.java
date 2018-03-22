package umbertoemonds.paper.data.rayon;

import com.pacoworks.rxpaper2.RxPaperBook;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import umbertoemonds.paper.data.rayon.model.Rayon;

/**
 * Created by umbertoemonds on 11/03/2018.
 */

public enum RayonManager {

    INSTANCE;

    public Single<Rayon> getRayon(String identifier){
        return RxPaperBook.with(Schedulers.io()).read(identifier);
    }

    public Completable writeRayon(String identifier, Rayon rayon){
        return RxPaperBook.with(Schedulers.io()).write(identifier, rayon);
    }

}

package test.pylogy.com.rxjavatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Log.i("yhf123","onNext() send 1");
                e.onNext(2);
                Log.i("yhf123","onNext() send 2");
                e.onNext(3);
                Log.i("yhf123","onNext() send 3");
                e.onComplete();
                Log.i("yhf123","onNext() send onComplete()");
                e.onNext(4);
                Log.i("yhf123","onNext() send 4");
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;
            private int i;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i("yhf123","onSubscribe()"+d.toString());
                mDisposable=d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.i("yhf123","onNext() get ="+integer);
                i++;
                if (i==2){
                    mDisposable.dispose();
                    Log.i("yhf123","Disposable isDisposed="+mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i("yhf123",e.toString(),e);
            }

            @Override
            public void onComplete() {
                Log.i("yhf123","onComplete()");
            }
        });
    }
}

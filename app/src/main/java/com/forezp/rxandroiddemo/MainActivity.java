package com.forezp.rxandroiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private final String TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // methodCreate();
       // methodJust();
       // operatorMap();
        //methodFrom();
        operatorFlatMap();

    }

    private void operatorFlatMap() {
        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                String [] strs={"1","2","3","4","5","6","7"};
                subscriber.onNext(Arrays.asList(strs));

            }
        }).flatMap(new Func1<List<String>, Observable<?>>() {
            @Override
            public Observable<?> call(List<String> list) {
                return Observable.from(list);
            }
        }).filter(new Func1<Object, Boolean>() {//filter 操作符，去掉“1”;
            @Override
            public Boolean call(Object o) {
                if(o.toString().equals("1"))return false;
                return true;
            }
        }).take(2).subscribe(new Action1<Object>() {//take 操作符，最多输出的数量

            @Override
            public void call(Object o) {
                Log.e(TAG,o.toString());
            }
        });
    }

    /**
     * from 方法创建被观察者接受list，一个一个输出
     */
    private void methodFrom() {
        String [] strs={"1","2","3","4"};
        Observable.from(Arrays.asList(strs)).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG,s);
            }
        });
    }

    /**
     * 操作符就是为了解决对Observable对象的变换的问题，操作符用于在Observable和最终的Subscriber之间修改
     * Observable发出的事件。RxJava提供了很多很有用的操作符。
     比如map操作符，就是用来把把一个事件转换为另一个事件的
     */
    private void operatorMap() {
        Observable.just("hi rxjava")
                .map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s+" ,你好rxjava";
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG,s+"");
            }
        });

        Observable.just("hi rxjava")
               .map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return s.hashCode();
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object s) {
                Log.e(TAG,s+"");
            }
        });
    }

    private void methodJust() {
        Observable.just("hi Rxjava2").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG,s);
            }
        });
    }

    private void methodCreate() {
        Observable<String> observable= Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hi RxJava");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,s);
            }
        };
        observable.subscribe(subscriber);
    }



}

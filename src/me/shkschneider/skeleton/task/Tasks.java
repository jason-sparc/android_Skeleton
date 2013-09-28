/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton.task;

import android.app.Activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.TimeHelper;

@SuppressWarnings("unused")
public class Tasks {

    private Activity mActivity;
    private List<Callable<Object>> mTasks;
    private Boolean mRunning;
    private Long mDuration;

    public Tasks(final Activity activity) {
        mActivity = activity;
        mTasks = new ArrayList<Callable<Object>>();
        mRunning = false;
        mDuration = 0L;
    }

    public Tasks() {
        mActivity = null;
        mTasks = new ArrayList<Callable<Object>>();
        mRunning = false;
        mDuration = 0L;
    }

    public void queue(final Runnable runnable) {
        if (runnable != null) {
            synchronized(mTasks) {
                mTasks.add(Executors.callable(runnable));
                mTasks.notify();
            }
        }
        else {
            LogHelper.w("Runnable was NULL");
        }
    }

    public Boolean running() {
        return mRunning;
    }

    public void run(final Callback callback) {
        mRunning = true;
        mDuration = TimeHelper.millitimestamp();
        try {
            Executors.newFixedThreadPool(mTasks.size()).invokeAll(mTasks);
        }
        catch (InterruptedException e) {
            LogHelper.w("InterruptedException: " + e.getMessage());
        }
        mRunning = false;
        if (callback != null) {
            mDuration = (TimeHelper.millitimestamp() - mDuration);
            if (mActivity != null) {
                mActivity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        callback.tasksCallback(mDuration);
                    }

                });
            }
            else {
                callback.tasksCallback(mDuration);
            }
        }
    }

    public void run() {
        run(null);
    }

    public interface Callback {

        public void tasksCallback(final Long duration);

    }

}

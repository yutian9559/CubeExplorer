package com.wanglei.cubeexplorer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.wanglei.kociemba.Kociemba;

public class MainActivity extends Activity
		implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener, View.OnClickListener {
	private static final String TAG = "MainActivity";
    private static final int DIALOG = 0;
	private int mIndex = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		copyFileToCache();
//		String facelets = "UBRLUFFUBLRUFRLLLRDBDRFDBBUDDBUDDLRFBFLDLBFFRFLRUBRDUU";
//		String result = Kociemba.solution(facelets, 24, 1000, 0, getCacheDir().toString(), null);
//		Log.i(TAG, "WL_DEBUG onCreate facelets = " + facelets + ", result = " + result);
		GridView cube = findViewById(R.id.cube);
		cube.setAdapter(new MainAdapter(this));
		cube.setOnItemClickListener(this);
		findViewById(R.id.search).setOnClickListener(this);
	}

	private void copyFileToCache() {
		File cache = getCacheDir();
		String[] list = null;
		try {
			list = getResources().getAssets().list("");
		} catch (IOException e) {
			Log.e(TAG, "WL_DEBUG copyFileToCache e1 = " + e, e);
		}
		if (list != null) {
			for (String fileString : list) {
				File file = new File(cache, fileString);
				if (!file.exists()) {
					AssetManager.AssetInputStream in = null;
					OutputStream out = null;
					try {
						in = (AssetManager.AssetInputStream) getResources().getAssets().open(fileString);
						byte[] byteGroup = new byte[1024];
						out = new FileOutputStream(file);
						int readTag;
						while ((readTag = in.read(byteGroup)) > 0) {
							out.write(byteGroup, 0, readTag);
						}
						in.close();
						out.close();
					} catch (Exception e) {
						Log.e(TAG, "WL_DEBUG copyFileToCache e = " + e, e);
					} finally {
						if (in != null) {
							try {
								in.close();
							} catch (IOException e) {
								Log.e(TAG, "WL_DEBUG copyFileToCache e = " + e, e);
							}
						}
						if (out != null) {
							try {
								out.close();
							} catch (IOException e) {
								Log.e(TAG, "WL_DEBUG copyFileToCache e = " + e, e);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		MainAdapter adapter = (MainAdapter) parent.getAdapter();
		int index = adapter.getIndexPublic(position);

		if (index > -1) {
			mIndex = index;
			showDialog(DIALOG);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog result = null;
        if (id == DIALOG) {
            result = new AlertDialog.Builder(this).setAdapter(new SpinnerAdapter(this), this).create();
        }
		return result;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which >= 0 && which <= 6) {
			GridView cube = findViewById(R.id.cube);
			MainAdapter adapter = (MainAdapter) cube.getAdapter();
			adapter.setState(mIndex, (byte) (which - 1));
		}
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();
        if (id == R.id.search) {
            GridView cube = findViewById(R.id.cube);
            MainAdapter adapter = (MainAdapter) cube.getAdapter();
            String facelets = adapter.getString();
            TextView solution = findViewById(R.id.solution);
            String solutionString = null;
            if (facelets != null) {
                solutionString = Kociemba.solution(facelets, 24, 1000, 0, getCacheDir().toString(), null);
            }
            if (solutionString == null || solutionString.isEmpty()) {
                solutionString = getString(R.string.none);
            }
            solution.setText(solutionString);
        }
	}
}

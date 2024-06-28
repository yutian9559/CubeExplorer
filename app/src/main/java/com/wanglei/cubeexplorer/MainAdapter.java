package com.wanglei.cubeexplorer;

import com.wanglei.widget.MyAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainAdapter extends MyAdapter {
	public static final byte STATE_U = 0;
	public static final byte STATE_R = 1;
	public static final byte STATE_F = 2;
	public static final byte STATE_D = 3;
	public static final byte STATE_L = 4;
	public static final byte STATE_B = 5;
    public static final int COLOR_INVALID = Color.GRAY;
	public static final int COLOR_U = Color.YELLOW;
	public static final int COLOR_R = Color.RED;
	public static final int COLOR_F = Color.BLUE;
	public static final int COLOR_D = Color.WHITE;
	public static final int COLOR_L = 0xffffa500;
	public static final int COLOR_B = Color.GREEN;
	private static final int[] VALID_INDEX = { 3, 4, 5, 15, 16, 17, 27, 28, 29, 42, 43, 44, 54, 55, 56, 66, 67, 68, 39,
			40, 41, 51, 52, 53, 63, 64, 65, 75, 76, 77, 87, 88, 89, 99, 100, 101, 36, 37, 38, 48, 49, 50, 60, 61, 62,
			45, 46, 47, 57, 58, 59, 69, 70, 71 };
	private final byte[] mBoard = { STATE_U, STATE_U, STATE_U, STATE_U, STATE_U, STATE_U, STATE_U, STATE_U, STATE_U,
			STATE_R, STATE_R, STATE_R, STATE_R, STATE_R, STATE_R, STATE_R, STATE_R, STATE_R, STATE_F, STATE_F, STATE_F,
			STATE_F, STATE_F, STATE_F, STATE_F, STATE_F, STATE_F, STATE_D, STATE_D, STATE_D, STATE_D, STATE_D, STATE_D,
			STATE_D, STATE_D, STATE_D, STATE_L, STATE_L, STATE_L, STATE_L, STATE_L, STATE_L, STATE_L, STATE_L, STATE_L,
			STATE_B, STATE_B, STATE_B, STATE_B, STATE_B, STATE_B, STATE_B, STATE_B, STATE_B };
    private final LayoutInflater mLayoutInflater;

	public MainAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return 12 * 9;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void bindView(int position, View v, ViewGroup parent) {
		int index = getIndex(position);
		int color = 0;
		if (index != -1) {
			color = getColor(mBoard[index]);
		}
		v.setBackgroundColor(color);
	}

	@Override
	protected View newView(int position, ViewGroup parent) {
		return mLayoutInflater.inflate(R.layout.grid_item, null);
	}

	private int getIndex(int position) {
		int result = -1;
		for (int i = 0; i < VALID_INDEX.length; i++) {
			if (position == VALID_INDEX[i]) {
				result = i;
				break;
			}
		}
		return result;
	}

	public int getIndexPublic(int position) {
		int result = getIndex(position);
		if (result % 9 == 4) {
			result = -1;
		}
		return result;
	}

	public static int getColor(byte state) {
		int result = COLOR_INVALID;
		switch (state) {
		case STATE_U:
			result = COLOR_U;
			break;
		case STATE_R:
			result = COLOR_R;
			break;
		case STATE_F:
			result = COLOR_F;
			break;
		case STATE_D:
			result = COLOR_D;
			break;
		case STATE_L:
			result = COLOR_L;
			break;
		case STATE_B:
			result = COLOR_B;
			break;
		}
		return result;
	}

	public String getString(byte state) {
		String result = null;
		switch (state) {
		case STATE_U:
			result = "U";
			break;
		case STATE_R:
			result = "R";
			break;
		case STATE_F:
			result = "F";
			break;
		case STATE_D:
			result = "D";
			break;
		case STATE_L:
			result = "L";
			break;
		case STATE_B:
			result = "B";
			break;
		}
		return result;
	}

	public void setState(int index, byte state) {
		mBoard[index] = state;
		notifyDataSetChanged();
	}

	public String getString() {
		String result = null;
		StringBuilder sb = new StringBuilder();
        for (byte b : mBoard) {
            String stateString = getString(b);
            if (stateString != null) {
                sb.append(stateString);
            } else {
                break;
            }
        }
		if (sb.length() == mBoard.length) {
			result = sb.toString();
		}
		return result;
	}
}
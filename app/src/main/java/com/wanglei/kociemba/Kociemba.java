package com.wanglei.kociemba;

public final class Kociemba {
	static {
		System.loadLibrary("kociemba");
	}

	public static native String solution(String facelets, int maxDepth, long timeOut, int useSeparator,
                                         String cache_dir, String pattern);
}
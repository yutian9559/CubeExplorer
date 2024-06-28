#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include "search.h"
#include "com_wanglei_kociemba_Kociemba.h"

JNIEXPORT jstring JNICALL Java_com_wanglei_kociemba_Kociemba_solution(
		JNIEnv *env, jclass, jstring facelets, jint maxDepth, jlong timeOut,
		jint useSeparator, jstring cache_dir, jstring pattern) {
	const char *native_facelets =
			facelets != nullptr ? env->GetStringUTFChars(facelets, 0) : nullptr;
	const char *native_cache_dir =
			cache_dir != nullptr ?
					env->GetStringUTFChars(cache_dir, 0) : nullptr;
	const char *native_pattern =
			pattern != nullptr ? env->GetStringUTFChars(pattern, 0) : nullptr;

	char *faceletsFinal = (char *)native_facelets;
	char patternized[64];
	if (native_pattern != nullptr) {
		patternize(faceletsFinal, (char *)native_pattern, patternized);
		faceletsFinal = patternized;
	}
	char *sol = solution(faceletsFinal, maxDepth, timeOut, useSeparator,
			native_cache_dir);

	if (native_facelets != nullptr) {
		env->ReleaseStringUTFChars(facelets, native_facelets);
	}
	if (native_cache_dir != nullptr) {
		env->ReleaseStringUTFChars(cache_dir, native_cache_dir);
	}
	if (native_pattern != nullptr) {
		env->ReleaseStringUTFChars(pattern, native_pattern);
	}
	jstring result = sol == nullptr ? nullptr : env->NewStringUTF(sol);
	if (sol != nullptr) {
		free(sol);
	}
	return result;
}

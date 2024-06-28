LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := kociemba
LOCAL_SRC_FILES := kociemba.cpp
LOCAL_SRC_FILES += coordcube.c
LOCAL_SRC_FILES += cubiecube.c
LOCAL_SRC_FILES += facecube.c
LOCAL_SRC_FILES += prunetable_helpers.c
LOCAL_SRC_FILES += search.c
LOCAL_SRC_FILES += solve.c

include $(BUILD_SHARED_LIBRARY)

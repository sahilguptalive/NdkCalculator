LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := NdkCalculator
LOCAL_SRC_FILES := fastoperations.c\
				calculatorbackend.c 
				    
include $(BUILD_SHARED_LIBRARY)

package com.lunastore.common;

import java.beans.PropertyEditorSupport;

public class CustomIntEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.trim().isEmpty()) {
            setValue(0); // 빈 문자열일 경우 기본값 0 설정
        } else {
            try {
                setValue(Integer.parseInt(text));
            } catch (NumberFormatException e) {
                setValue(0); // 유효하지 않은 값일 경우 기본값 0 설정
            }
        }
    }
}
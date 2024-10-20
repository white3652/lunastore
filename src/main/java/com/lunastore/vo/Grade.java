package com.lunastore.vo;

public enum Grade {
    STANDARD(1),
    PREMIUM(2),
    VIP(3);

    private final int code;

    Grade(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Grade fromCode(int code) {
        for (Grade grade : Grade.values()) {
            if (grade.getCode() == code) {
                return grade;
            }
        }
        return STANDARD;
    }

    @Override
    public String toString() {
        switch (this) {
            case STANDARD:
                return "Standard";
            case PREMIUM:
                return "Premium";
            case VIP:
                return "VIP";
            default:
                return "Standard";
        }
    }
}
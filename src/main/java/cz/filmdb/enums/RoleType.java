package cz.filmdb.enums;


import jdk.jfr.Category;

public enum RoleType {
    ACTOR("A"), CREATOR("C"), MAKEUP_ARTIST("MA"), DIRECTOR("D"), WRITER("W");

    final private String code;

    private RoleType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

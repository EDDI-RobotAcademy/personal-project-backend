package com.example.demo.board.entity;

public enum BoardCategoryType {
    MAIN("Main"),
    SPRING("Spring"),
    PYTHON("Python"),
    VUE("Vue"),
    REACT("React"),
    QUESTION("Question");

    private final String categoryValue;

    BoardCategoryType(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String getCategoryValue() {
        return categoryValue;
    }
}

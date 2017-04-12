package br.com.viasoft.controller;

public interface SceneAttributes {

    String getTitle();

    default void execButtonNext() {

    }

    default void execButtonPrev() {

    }

    default void execButtonStep() {

    }

    default boolean allowForward() {
        return true;
    }

    default String getMessageNotAllwForward() {
        return "Erro";
    }

}

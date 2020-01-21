/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

/**
 *
 *
 */
public class Palabra implements Comparable<Palabra> {

    private String word;
    private String definition;
    

    public Palabra() {
        this.word = "";
        this.definition = "";
    }

    public Palabra(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public String getDefinition() {
        return definition;
    }

    public String getWord() {
        return word;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public int compareTo(Palabra word) {
        return this.word.compareTo(word.getWord());
    }

    @Override
    public String toString() {
        return word + ": " + definition;
    }

    

}

package Game.Characters;

import Game.Skill.*;

import java.util.Arrays;

public  class Book {

    private Skill[] book;
    private int number;


    public Book() {
        book = new Skill[10];
        for(int i=0; i<book.length; i++){
            book[i] = new Blink();
        }
        number = 0;
    }

    public Skill[] getBook() {
        return book;
    }


    public void add(Skill skill) {this.book[number] = skill;}

    @Override
    public String toString() {
        String message = "";
        for(int i=0; i<book.length; i++){
            message +=" -  " + book[i].toString() + "\n\n";
        }
        return message;
    }
}

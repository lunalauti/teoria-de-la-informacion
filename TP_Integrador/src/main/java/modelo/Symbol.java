package main.java.modelo;

import java.util.Objects;

public class Symbol {
    private char symbol;
    private int counter;

    public Symbol(char symbol){
        this.symbol = Character.toLowerCase(symbol);
        this.counter = 0;
    }

    public void counterPlus(){
        this.counter++;
    }

    public char getSymbol(){
        return this.symbol;
    }

    public int getCounter(){
        return this.counter;
    }

    public String toString(){
        return this.symbol + ": " + this.counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol1 = (Symbol) o;
        return symbol == symbol1.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}

package CDD.util;

public class Tuple<T, U> {

    private T frst;
    private U scnd;

    public Tuple(T frst, U scnd){
        this.frst = frst;
        this.scnd = scnd;
    }

    public T frst(){
        return frst;
    }

    public U scnd(){
        return scnd;
    }

    @Override
    public boolean equals(Object tuple){
        if(!tuple.getClass().equals(this.getClass())) return false;
        return frst == ((Tuple<T, U>) tuple).frst && scnd == ((Tuple<T, U>) tuple).scnd;
    }
    
}

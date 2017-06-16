package ar.com.betianaminio.workingwithsqlite.models;


public class Exercise {

    private int    id_exercise;
    private String name;
    private String description;
    private String level;

    public Exercise(){


    }

    public Exercise(int id, String name, String description, String level ){

        this.id_exercise = id;
        this.name = name;
        this.description = description;
        this.level = level;
    }


    public int getId(){

        return this.id_exercise;
    }

    public String getName(){

        return this.name;
    }

    public String getDescription(){

        return this.description;
    }

    public String getLevel(){

        return this.level;
    }

    public void setName( String name ){

        this.name = name;
    }

    public void setDescription( String description ){

        this.description = description;
    }

    public void setLevel( String level ){

        this.level = level;
    }
}

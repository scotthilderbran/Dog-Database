// Dog class for Dog objects

public class Dog {

    private int id;
    private String name;
    private String breed;
    private String sex;
    private int age;
    private int weight;
    private String rental;

    Dog(int id, String name, String breed,String sex,int age,int weight,String rental){ // Dog object constructor
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.rental = rental;
    }

    // Dog object get values methods

    public int getId(){
        return id;
    }
    public String getName(){ return name; }
    public String getBreed(){
        return breed;
    }
    public String getSex(){
        return sex;
    }
    public int getAge(){
        return age;
    }
    public int getWeight(){
        return weight;
    }
    public String getRental(){ return rental; }

    // Dog object set values methods

    public void setID(int id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setBreed(String breed){
        this.breed=breed;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void setAge(int age){
        this.age=age;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public void setRental(String rental){
        this.rental = rental;
    }

}

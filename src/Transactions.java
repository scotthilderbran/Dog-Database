//Transaction class to create transaction type objects
public class Transactions {

    private int rentalId;
    private int dogId;
    private String dogName;
    private String customer;
    private String email;
    private String rentalDate;
    private String returnDate;
    private String breed;

    Transactions(int rentalId, int dogId,String dogName, String customer, String email, String rentalDate, String returnDate,String breed) { //Transaction object constructor
        this.rentalId = rentalId;
        this.dogId = dogId;
        this.dogName = dogName;
        this.customer = customer;
        this.email = email;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.breed = breed;
    }

    //Transaction get values methods

    public int getRentalId(){
        return rentalId;
    }
    public int getDogId(){
        return dogId;
    }
    public String getDogName(){
        return dogName;
    }
    public String getCustomer(){
        return customer;
    }
    public String getEmail(){
        return email;
    }
    public String getRentalDate(){
        return rentalDate;
    }
    public String getReturnDate(){
        return returnDate;
    }
    public String getBreed(){
        return breed;
    }
    public String getYear(){ //Get individual year string for Breed report sorting
        String[] dateInput = rentalDate.split("/");
        String yearOutput = dateInput[2];
        return yearOutput;
    }
    public String getMonth(){ //Get individual month string for Breed report sorting
        String[] dateInput = rentalDate.split("/", -2);
        String monthOutput = dateInput[0];
        return monthOutput;
    }

    //Transaction set values methods

    public void setRentalId(int rentalId){
        this.rentalId = rentalId;
    }
    public void setDogId(int dogId){
        this.dogId = dogId;
    }
    public void setDogName(String dogName){
        this.dogName=dogName;
    }
    public void setCustomer(String customer){
        this.customer=customer;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setRentalDate(String rentalDate){
        this.returnDate=rentalDate;
    }
    public void setReturnDate(String returnDate){
        this.returnDate=returnDate;
    }
}


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Main main = new Main(); //Creates new main object
        HashMap<Integer, Dog> dogHashMap = new HashMap<Integer,Dog>(); //Creates new HashMap for Dog Objects
        HashMap<Integer, Transactions> transactionsHashMap = new HashMap<Integer, Transactions>();//Creates new HashMap for Dog Objects
        Scanner input = new Scanner(System.in);
        FileManage fileWrite = new FileManage();
        Report reporting = new Report();
        dogHashMap = fileWrite.loadFile("dogs.txt"); //Update the currently loaded hashmap with info from dogs.txt
        transactionsHashMap = fileWrite.loadTransactions("transactions.txt"); //Update the transaction hashmap from transactions.txt
        Boolean firstRun = true;
        while (firstRun) { // Main menu loop
            Boolean running = true;
            System.out.println("Welcome to Scott's Dog Management Interface\nPlease choose from the following options:\n[1]View, Add, Edit, or Delete a dog\n[2]Rent a Dog\n[3]Return a dog\n[4]View Reports\n[5]Process Batch updates\n[6]Exit");
            int firstChoice = input.nextInt();
            switch (firstChoice) {
                case 1:{ //View,Add,Edit,and Delete functions
                    while (running) { // Sub menu option 1 loop
                        System.out.println("----------------------------------");
                        System.out.println("[1]View dogs\n[2]Add a dog\n[3]Edit a dog\n[4]Delete a dog\n[5]Exit to main menu");
                        int choice = input.nextInt();
                        switch (choice) {
                            case 1:{ //View Dogs
                                main.printDogs(dogHashMap); //Calls function to print object variables
                                break;
                            }
                            case 2:{ //Add Dogs
                                System.out.println("Please enter the dog's name");
                                String name = input.next();
                                System.out.println("Please enter the dog's Breed");
                                String breed = input.next();
                                String sex = main.stringInputVerification("M","F","sex");
                                int age = main.positiveIntVerification("age");
                                int weight = main.positiveIntVerification("weight");
                                String rental = main.stringInputVerification("T","F","rental indicator");
                                int id = main.idGenerator(dogHashMap);
                                dogHashMap.put(id, new Dog(id, name, breed, sex, age, weight,rental));
                                break;
                            }
                            case 3:{ // Edit dogs
                                main.printDogs(dogHashMap);
                                Boolean editRun = true;
                                while (editRun) { // Dog ID loop
                                    System.out.println("Please enter the dog's id to edit:");
                                    int dogId = input.nextInt();
                                    if (dogHashMap.containsKey(dogId)) {
                                        System.out.println("\n");
                                        System.out.println("----------------------------------");
                                        main.printSingle(dogHashMap,dogId);
                                        System.out.println("Edit:\n[1]Name\n[2]Breed\n[3]Sex\n[4]Age\n[5]Weight\n[6]Rental Availability");
                                        int editChoice = input.nextInt();
                                        switch (editChoice) {
                                            case 1:{ // Name
                                                System.out.println("The current name is: " + dogHashMap.get(dogId).getName());
                                                System.out.println("Please enter the new name:");
                                                String newName = input.next();
                                                dogHashMap.get(dogId).setName(newName);
                                                fileWrite.writeFile(dogHashMap);
                                                break;
                                            }
                                            case 2:{ // Breed
                                                System.out.println("The current breed is: " + dogHashMap.get(dogId).getBreed());
                                                System.out.println("Please enter the new breed:");
                                                String newBreed = input.next();
                                                dogHashMap.get(dogId).setBreed(newBreed);
                                                fileWrite.writeFile(dogHashMap);
                                                break;
                                            }
                                            case 3:{ // Sex
                                                System.out.println("The current sex is: " + dogHashMap.get(dogId).getSex());
                                                boolean changeSexRun=true ;
                                                dogHashMap.get(dogId).setSex(main.stringInputVerification("M","F","sex"));
                                                fileWrite.writeFile(dogHashMap);
                                                break;

                                            }
                                            case 4:{ // Age
                                                System.out.println("The current age is: " + dogHashMap.get(dogId).getAge());
                                                boolean changeAgeRun = true;
                                                dogHashMap.get(dogId).setAge(main.positiveIntVerification("age"));
                                                fileWrite.writeFile(dogHashMap);
                                                break;
                                            }
                                            case 5:{ // Weight
                                                System.out.println("The current weight is: " + dogHashMap.get(dogId).getWeight());
                                                    dogHashMap.get(dogId).setWeight(main.positiveIntVerification("Weight"));
                                                    fileWrite.writeFile(dogHashMap);
                                                }
                                                break;
                                            case 6:{ // Rent
                                                System.out.println("The current dog is available to rent: " + dogHashMap.get(dogId).getRental());
                                                dogHashMap.get(dogId).setRental(main.stringInputVerification("T","F","rental indicator"));
                                                fileWrite.writeFile(dogHashMap);
                                            }
                                            default:{
                                                System.out.println("Please enter a valid choice");
                                            }
                                        }
                                        editRun=false;
                                    }else{
                                        System.out.println("Error: Dog ID not found.");
                                    }

                                }
                                break;
                            }
                            case 4:{ // Delete dogs
                                boolean deleteRun = true;
                                while(deleteRun){
                                    main.printDogs(dogHashMap);
                                    System.out.println("Please enter the dog's id to delete:");
                                    int dogId = input.nextInt();
                                    if(dogHashMap.containsKey(dogId)){
                                        System.out.println("Press [Y] to confirm deletion of " + dogHashMap.get(dogId).getName());
                                        String deleteChoice = input.next();
                                        if (deleteChoice.equals("Y")){
                                            dogHashMap.remove(dogId);
                                            fileWrite.writeFile(dogHashMap);
                                            deleteRun = false;
                                        }else{
                                            System.out.println("Dog not deleted");
                                            break;
                                        }
                                    }else{
                                        System.out.println("Error: Dog ID not found.");
                                    }
                                }
                                break;
                            }
                            case 5:{ // Exit code
                                fileWrite.writeFile(dogHashMap);
                                running = false;
                                break;
                            }
                            default:{
                                System.out.println("Please enter a valid choice");
                            }
                        }
                    }
                    break;
                }
                case 2:{ // Rent A Dog
                    System.out.println("The following dogs are available for rent: \n\n");
                    main.dogsForRent(dogHashMap); //Calls function to print object variables
                    boolean rentalRun = true;
                    while(rentalRun){
                        ArrayList rentalArray = main.populateRentalMap(dogHashMap,"T");
                        System.out.println("Please enter a dog id to rent:");
                        int rentalChoice = input.nextInt();
                        if(rentalArray.contains(rentalChoice)){
                            System.out.println("Please enter the customer's name:");
                            String rentalName = input.next();
                            System.out.println("Please enter the customer's email:");
                            String rentalEmail = input.next();
                            String rentalDate = main.getDate();
                            String returnDate = "null";
                            int rentalId = main.rentalIdGenerator(transactionsHashMap);
                            transactionsHashMap.put(rentalId,new Transactions(rentalId, rentalChoice,dogHashMap.get(rentalChoice).getName(),rentalName,rentalEmail,rentalDate,returnDate,dogHashMap.get(rentalChoice).getBreed()));
                            dogHashMap.get(rentalChoice).setRental("F");
                            fileWrite.writeTransactions(transactionsHashMap,"transactions.txt");
                            fileWrite.writeFile(dogHashMap);
                            System.out.println("Dog Added!");
                            rentalRun=false;
                        }else{
                            System.out.println("Please enter a valid id");
                        }
                    }
                    break;
                    }
                case 3:{ // Return a Dog
                    System.out.println("Return a dog");
                    System.out.println("The following dogs are checked out: \n\n");
                    main.dogsRented(transactionsHashMap);
                    ArrayList returnTransactionsArray = main.populateTransactionRentalMap(transactionsHashMap);
                    boolean returnRun = true;
                    while(returnRun){
                        System.out.println("Please enter the Rental id:");
                        int returningId = input.nextInt();
                        if(returnTransactionsArray.contains(returningId)){
                            System.out.println("Please enter the customers email:");
                            String returningEmail = input.next();
                            String date = main.getDate();
                            transactionsHashMap.get(returningId).setReturnDate(date);
                            int editID = transactionsHashMap.get(returningId).getDogId();
                            dogHashMap.get(editID).setRental("T");
                            fileWrite.writeTransactions(transactionsHashMap,"transactions.txt");
                            fileWrite.writeFile(dogHashMap);
                            returnRun = false;
                        }else{
                            System.out.println("Please enter a valid id");
                        }
                    }
                    break;
                }
                case 4:{ // Pull reports
                    boolean reportRun = true;
                    while (reportRun){
                        System.out.println("What type of report do you want to pull?");
                        System.out.println("[1]Individual - View transactions by customer email.");
                        System.out.println("[2]Historical Overview - View all rentals sorted by customer, date, and totals");
                        System.out.println("[3]Historical Breed Reports - View report of rentals by breed");
                        System.out.println("[4]Exit to main menu");
                        int reportChoice = input.nextInt();
                        switch (reportChoice) {
                            case 1:{
                                System.out.println("Please enter the customer's email");
                                String reportEmail = input.next();
                                HashMap<Integer,Transactions> report = reporting.pullReportIndividual(transactionsHashMap,reportEmail);
                                main.printTransactions(report);
                                System.out.println("Would you like to save this report to a file?");
                                String saveReportChoice = main.stringInputVerification("Y","N","choice");
                                if (saveReportChoice.equals("Y")){
                                    System.out.println("Please enter the file name to save");
                                    String reportPath = input.next();
                                    String reportPathFinal = reportPath +".txt";
                                    fileWrite.writeIndividualReport(report,reportPathFinal);
                                }
                                break;
                            }
                            case 2:{ // Historical Customer Rental report
                                Map<String,List<Transactions>> sortedList = reporting.pullReportHistorical(transactionsHashMap);
                                main.printHistoricalReport(sortedList);
                                System.out.println("Would you like to save this report to a file?");
                                String saveReportChoice = main.stringInputVerification("Y","N","choice");
                                if (saveReportChoice.equals("Y")){
                                    System.out.println("Please enter the file name to save");
                                    String reportPath = input.next();
                                    String reportPathFinal = reportPath +".txt";
                                    fileWrite.writeHistoricalReport(sortedList,reportPathFinal);
                                }
                                break;
                            }
                            case 3:{ // Historical Breed Report
                                Map<String,Map<String, Map<String,List<Transactions>>>> breedSorted = reporting.pullReportBreed(transactionsHashMap);
                                main.printBreedReport(breedSorted);
                                System.out.println("Would you like to save this report to a file?");
                                String saveReportChoice = main.stringInputVerification("Y","N","choice");
                                if (saveReportChoice.equals("Y")){
                                    System.out.println("Please enter the file name to save");
                                    String reportPath = input.next();
                                    String reportPathFinal = reportPath +".txt";
                                    fileWrite.writeBreedReport(breedSorted,reportPathFinal);
                                }
                                break;
                            }
                            case 4:{
                                reportRun=false;
                            }
                            default:{
                                System.out.println("Please enter a valid choice");
                            }
                        }
                    }
                    break;
                }
                case 5:{ // Batch updates
                boolean batchUpdate = true;
                while(batchUpdate){
                    try{
                        System.out.println("Please enter the path of the transaction file");
                        String transactionPath = input.next();
                        dogHashMap = fileWrite.mergeData(transactionPath,dogHashMap);
                        System.out.println("Here is the updated dog list:");
                        main.printDogs(dogHashMap);
                        fileWrite.writeFile(dogHashMap);
                        batchUpdate = false;
                    }catch(Exception e){
                        System.out.println("Error file not found");
                    }
                }
                break;
                }
                case 6:{ // Exit out of program
                    System.out.println("Exiting");
                    firstRun = false;
                    break;
                }
                default:{
                    System.out.println("Please enter a valid choice");
                }
            }
        }
    }

    // Main methods

    public void printTransactions(HashMap<Integer,Transactions> map){ // Print all transaction objects stored in Transaction HashMap
        Set setEntry = map.entrySet();
        Iterator iteration = setEntry.iterator();
        for (Map.Entry<Integer, Transactions> entry : map.entrySet()) {
            int key = entry.getKey();
            System.out.println("Rental ID: "+map.get(key).getRentalId()+"\nDog ID: "+map.get(key).getDogId()+"\nCustomer Name: "+map.get(key).getCustomer()+"\nCustomer Email: "+map.get(key).getEmail()+"\nRental Date: "+map.get(key).getRentalDate()+"\nReturn Date: "+map.get(key).getReturnDate()+"\n");
            }
        }

    public void printDogs(HashMap<Integer,Dog> map){ // Prints all Dog objects stored in dog HashMap
        Set setEntry = map.entrySet();
        Iterator iteration = setEntry.iterator();
        while(iteration.hasNext()){
            Map.Entry mapped = (Map.Entry)iteration.next();
            System.out.println("ID: "+mapped.getKey()+"\nName: "+map.get(mapped.getKey()).getName()+"\nBreed: "+map.get(mapped.getKey()).getBreed()+"\nSex: "+map.get(mapped.getKey()).getSex()+"\nAge: "+map.get(mapped.getKey()).getAge()+"\nWeight: "+map.get(mapped.getKey()).getWeight()+"\nRental Status: " + map.get(mapped.getKey()).getRental()+"\n");
        }
    }

    public void printSingle(HashMap<Integer, Dog> map,int id){ // Prints out data for single dog object depending on ID input
        System.out.println("ID: "+id);
        System.out.println("Name: "+map.get(id).getName());
        System.out.println("Breed: "+map.get(id).getBreed());
        System.out.println("Sex: "+map.get(id).getSex());
        System.out.println("Age: " + map.get(id).getAge());
        System.out.println("Weight: "+map.get(id).getWeight());
        System.out.println("------");
    }

    public void dogsForRent(HashMap<Integer,Dog> map) { // Prints available dogs for rent based on rental marker property
        Set setEntry = map.entrySet();
        Iterator iteration = setEntry.iterator();
        while (iteration.hasNext()) {
            Map.Entry mapped = (Map.Entry)iteration.next();
            if(map.get(mapped.getKey()).getRental().equals("T")){
                System.out.println("ID: "+mapped.getKey()+"\nName: "+map.get(mapped.getKey()).getName()+"\nBreed: "+map.get(mapped.getKey()).getBreed()+"\nSex: "+map.get(mapped.getKey()).getSex()+"\nAge: "+map.get(mapped.getKey()).getAge()+"\nWeight: "+map.get(mapped.getKey()).getWeight()+"\nRental Status: " + map.get(mapped.getKey()).getRental()+"\n");
            }
        }
    }

    public void dogsRented(HashMap<Integer,Transactions> map) { // Prints out list of dogs where return date is currently "null"
        Set setEntry = map.entrySet();
        Iterator iteration = setEntry.iterator();
        while (iteration.hasNext()) {
            Map.Entry mapped = (Map.Entry)iteration.next();
            String returnDate = map.get(mapped.getKey()).getReturnDate();
            if(returnDate.equals("null")){
                System.out.println("Rental ID: "+map.get(mapped.getKey()).getRentalId()+"\nDog ID: "+map.get(mapped.getKey()).getDogId()+"\nDog name: "+map.get(mapped.getKey()).getDogName()+"\nCustomer Name: "+map.get(mapped.getKey()).getCustomer()+"\nCustomer Email: "+map.get(mapped.getKey()).getEmail());
            }
        }
    }

    public ArrayList<Integer> populateRentalMap(HashMap<Integer,Dog> map,String availabilityType){ // Populates rental map for program to check if ID entered on rental screen is actually dog rented
        ArrayList<Integer> outputMap = new ArrayList<>();
        Set setEntry = map.entrySet();
        Iterator iteration = setEntry.iterator();
        while (iteration.hasNext()) {
            Map.Entry mapped = (Map.Entry)iteration.next();
            if(map.get(mapped.getKey()).getRental().equals(availabilityType)){
                outputMap.add(map.get(mapped.getKey()).getId());
            }
        }
    return outputMap;
    }

    public ArrayList<Integer> populateTransactionRentalMap(HashMap<Integer,Transactions> map) { // Populates transaction rental ArrayList for further verification if id enter has been returned or not
        ArrayList<Integer> outputMap = new ArrayList<>();
        Set setEntry = map.entrySet();
        Iterator iteration = setEntry.iterator();
        while (iteration.hasNext()) {
            Map.Entry mapped = (Map.Entry) iteration.next();
            if (map.get(mapped.getKey()).getReturnDate().equals("null")) {
                outputMap.add(map.get(mapped.getKey()).getRentalId());
            }
        }
        return outputMap;
    }
    public String getDate(){ // Gets current date and returns in string format
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date current = Calendar.getInstance().getTime();
        String currentDate = df.format(current);
        return currentDate;
    }

    public int idGenerator(HashMap<Integer,Dog> map){ // Generates new dog ID from 100-1100 that is not currently used by any Dog object
        int primaryKey = 1;
        Random rand = new Random();
        boolean runId=true;
        while(runId){
            primaryKey = rand.nextInt(1000)+100;
            if (!map.containsKey(primaryKey)) {
                runId=false;
                break;
            }else{
            }
        }
        return primaryKey;
    }

    public int rentalIdGenerator(HashMap<Integer,Transactions> map){ // Generates new transaction ID from 100-110 that is not currently used by any Transaction object
        int primaryKey = 1;
        Random rand = new Random();
        boolean runId=true;
        while(runId){
            primaryKey = rand.nextInt(1000)+100;
            if (!map.containsKey(primaryKey)) {
                runId=false;
                break;
            }else{
            }
        }
        return primaryKey;
    }

    public int positiveIntVerification(String choice){ // Forces user to enter positive integer as option
        Scanner intIn = new Scanner(System.in);
        int output = 1;
        boolean verifyRun = true;
        while(verifyRun){
            System.out.println("Please enter the dog's "+ choice);
            int newInput = intIn.nextInt();
            if (newInput>0){
                output=newInput;
                verifyRun = false;
            }else {
                System.out.println("Please enter a positive integer");
            }
        }
        return output;
    }

    public String stringInputVerification(String firstOption, String secondOption, String prompt){ // Verifies user input equals one of two options
        Scanner verifyIn = new Scanner(System.in);
        boolean verifyRun = true;
        String output = "";
        while(verifyRun){
            System.out.println("Please enter "+prompt+" "+firstOption+" or "+secondOption);
            String newInput = verifyIn.next();
            if(newInput.equals(firstOption)||newInput.equals(secondOption)){
                output=newInput;
                verifyRun=false;
            }else{
                System.out.println("Please enter either " +firstOption+" or " + secondOption);
            }
        }
        return output;
    }

    public void printHistoricalReport(Map<String,List<Transactions>> map){ // Prints lines of historical reports to console

        for (Map.Entry<String, List<Transactions>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<Transactions> newList = map.get(key);
            for (Transactions t:newList){
                System.out.println("Rental ID: "+t.getRentalId()+"\nDog ID: "+t.getDogId()+"\nDog Name: "+t.getDogName()+"\nCustomer Name: "+t.getCustomer()+"\nCustomer Email: "+t.getEmail()+"\nRental Date: "+t.getRentalDate()+"\nReturn Date: "+t.getReturnDate()+"\n");
            }
            System.out.println("Total Rentals for: "+key+": ["+map.get(key).size()+"]\n\n");
            System.out.println("-------------------------------------------");
        }
    }

    public void printBreedReport(Map<String,Map<String, Map<String,List<Transactions>>>> map) { // Prints lines of breed report to console
        Set setEntry = map.entrySet();
        Iterator iteration = setEntry.iterator();
        while (iteration.hasNext()) {
            int totalCount = 0;
            Map.Entry mapped = (Map.Entry) iteration.next();
            Set setEntryTwo = map.get(mapped.getKey()).entrySet();
            System.out.println("\nDog Breed: "+mapped.getKey()+"\n");
            Iterator iterationTwo = setEntryTwo.iterator();
            while (iterationTwo.hasNext()) {
                int yearCount = 0;
                Map.Entry mappedTwo = (Map.Entry) iterationTwo.next();
                System.out.println("\n\tYear: "+mappedTwo.getKey()+"\n");
                Set setEntryThree = map.get(mapped.getKey()).get(mappedTwo.getKey()).entrySet();
                Iterator iterationThree = setEntryThree.iterator();
                while (iterationThree.hasNext()){
                    int monthCount = 0;
                    Map.Entry mappedThree = (Map.Entry) iterationThree.next();
                    System.out.println("\n\t\tMonth: "+mappedThree.getKey()+"\n\n");
                    for (Transactions t : map.get(mapped.getKey()).get(mappedTwo.getKey()).get(mappedThree.getKey())){
                        System.out.println("\t\t\tRental ID: "+t.getRentalId()+"\n\t\t\tDog Name: "+t.getDogName()+"\n\t\t\tCustomer Name: "+t.getCustomer()+"\n\t\t\tCustomer Email: "+t.getEmail()+"\n\t\t\tRental Date: "+t.getRentalDate()+"\n\t\t\tReturn Date: "+t.getReturnDate()+"\n\t\t\tBreed: "+t.getBreed()+"\n");
                        monthCount++;
                    }
                    System.out.println("\n\t\tTotal Rentals for "+mappedThree.getKey()+" Month: "+monthCount+"\n\n");
                    yearCount = yearCount+monthCount;
                }
                System.out.println("\n\tTotal Rentals for "+mappedTwo.getKey()+" Year: "+yearCount+"\n\n");
                totalCount = totalCount + yearCount;
            }
            System.out.println("\nTotal Rentals for "+mapped.getKey()+" Breed: "+totalCount);
            System.out.println("\n-------------------------------------------");
        }
    }
}

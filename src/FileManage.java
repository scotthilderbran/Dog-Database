import java.util.*;
import java.io.*;

// Class to write and load transaction file, dog file, and any report that needs writing to txt file

public class FileManage {

    public HashMap<Integer, Dog> loadFile(String path) throws IOException { // Loads data file of Dogs.txt to HashMap
        HashMap<Integer, Dog> temp = new HashMap<Integer, Dog>();
        FileInputStream fileInput = new FileInputStream(path);
        Scanner scan = new Scanner(fileInput);
        scan.useDelimiter("\n");
        while (scan.hasNextLine()) {
            String row = scan.nextLine();
            String[] input = row.split("#", -2);
            String idParsable = input[0];
            String name = input[1];
            String breed = input[2];
            String sex = input[3];
            String ageParsable = input[4];
            String weightParsable = input[5];
            String rental = input[6];
            int idFinal = Integer.parseInt(idParsable);
            int ageFinal;
            int weightFinal;
            if (ageParsable.equals("")) {
                ageFinal = 0;
            } else {
                ageFinal = Integer.parseInt(ageParsable);
            }
            if (weightParsable.equals("")) {
                weightFinal = 0;
            } else {
                weightFinal = Integer.parseInt(weightParsable);
            }
            try {
                temp.put(idFinal, new Dog(idFinal, name, breed, sex, ageFinal, weightFinal, rental));
            } catch (NumberFormatException ex) {
                System.out.println("Number format error");
            }
        }
        scan.close();
        return temp;
    }

    public void writeFile(HashMap<Integer, Dog> map) { //Writes the current HashMap of dog Objects to Dogs.txt data file
        try {
            BufferedWriter bwr = new BufferedWriter(new FileWriter("dogs.txt"));
            Set setEntry = map.entrySet();
            Iterator iteration = setEntry.iterator();
            while (iteration.hasNext()) {
                Map.Entry mapped = (Map.Entry) iteration.next();
                bwr.write(mapped.getKey() + "#" + map.get(mapped.getKey()).getName() + "#" + map.get(mapped.getKey()).getBreed() + "#" + map.get(mapped.getKey()).getSex() + "#" + map.get(mapped.getKey()).getAge() + "#" + map.get(mapped.getKey()).getWeight() + "#" + map.get(mapped.getKey()).getRental() + "\n");
            }
            bwr.close();
        } catch (Exception ioe) {
            System.out.println("Error");
        }
    }

    public void writeTransactions(HashMap<Integer, Transactions> map, String path) { //Writes current HashMap of Transactions to transactions.txt file
        try {
            BufferedWriter bwr = new BufferedWriter(new FileWriter(path));
            Set setEntry = map.entrySet();
            Iterator iteration = setEntry.iterator();
            while (iteration.hasNext()) {
                Map.Entry mapped = (Map.Entry) iteration.next();
                bwr.write(map.get(mapped.getKey()).getRentalId() + "#" + map.get(mapped.getKey()).getDogId() + "#" + map.get(mapped.getKey()).getDogName() + "#" + map.get(mapped.getKey()).getCustomer() + "#" + map.get(mapped.getKey()).getEmail() + "#" + map.get(mapped.getKey()).getRentalDate() + "#" + map.get(mapped.getKey()).getReturnDate()+"#"+map.get(mapped.getKey()).getBreed() + "\n");
            }
            bwr.close();
        } catch (Exception ioe) {
            System.out.println("Error");
        }
    }

    public HashMap<Integer, Transactions> loadTransactions(String path) throws IOException { //Loads Transactions.txt into a HashMap of Transaction Objects
        HashMap<Integer, Transactions> temp = new HashMap<Integer, Transactions>();
        FileInputStream fileInput = new FileInputStream(path);
        Scanner scan = new Scanner(fileInput);
        scan.useDelimiter("\n");
        while (scan.hasNextLine()) {
            String row = scan.nextLine();
            String[] input = row.split("#");
            String transactionIdParsable = input[0];
            String dogIdParsable = input[1];
            String dogName = input[2];
            String customer = input[3];
            String email = input[4];
            String rentalDate = input[5];
            String returnDate = input[6];
            String breed = input [7];
            int transactionidFinal = Integer.parseInt(transactionIdParsable);
            int dogIdFinal = Integer.parseInt(dogIdParsable);
            try {
                temp.put(transactionidFinal, new Transactions(transactionidFinal, dogIdFinal, dogName, customer, email, rentalDate, returnDate,breed));
            } catch (NumberFormatException ex) {
                System.out.println("Number format error");
            }
        }
        scan.close();
        return temp;
    }

    public HashMap<Integer, Dog> mergeData(String path, HashMap<Integer, Dog> dogHashMap) throws IOException { //Implements Merge function replacing data in DogHashmap with any non Empty fields in add.txt
        FileManage loader = new FileManage();
        HashMap<Integer, Dog> temp = new HashMap<Integer, Dog>();
        temp = loader.loadFile(path);
        for (Map.Entry<Integer, Dog> entry : temp.entrySet()) {
            int key = entry.getKey();
            System.out.println(temp.get(key).getName());
            if (dogHashMap.containsKey(key)) {
                if (!temp.get(key).getName().equals("")) {
                    dogHashMap.get(key).setName(temp.get(key).getName());
                }
                if (!temp.get(key).getBreed().equals("")) {
                    dogHashMap.get(key).setBreed(temp.get(key).getBreed());
                }
                if (!temp.get(key).getSex().equals("")) {
                    dogHashMap.get(key).setSex(temp.get(key).getSex());
                }
                if (temp.get(key).getAge() != 0) {
                    dogHashMap.get(key).setAge(temp.get(key).getAge());
                }
                if (temp.get(key).getWeight() != 0) {
                    dogHashMap.get(key).setWeight(temp.get(key).getWeight());
                }
                if (!temp.get(key).getRental().equals("")) {
                    dogHashMap.get(key).setRental(temp.get(key).getRental());
                }
            } else {
                dogHashMap.put(key, temp.get(key));
            }
            for (Map.Entry<Integer, Dog> secondEntry : dogHashMap.entrySet()) {
                int secondKey = secondEntry.getKey();
                if (!temp.containsKey(secondKey)) {
                    dogHashMap.remove(secondKey);
                }
            }
        }
        return dogHashMap;
    }

    public void writeIndividualReport(HashMap<Integer,Transactions>map,String path){ // Writes individual report to txt file
        try {
            BufferedWriter bwr = new BufferedWriter(new FileWriter(path));
            Set setEntry = map.entrySet();
            Iterator iteration = setEntry.iterator();
            while (iteration.hasNext()) {
                Map.Entry mapped = (Map.Entry) iteration.next();
                bwr.write("Rental ID: "+map.get(mapped.getKey()).getRentalId() + "\nDog ID: " + map.get(mapped.getKey()).getDogId() + "\nDog Name: " + map.get(mapped.getKey()).getDogName() + "\nCustomer: " + map.get(mapped.getKey()).getCustomer() + "\nEmail: " + map.get(mapped.getKey()).getEmail() + "\nRental Date: " + map.get(mapped.getKey()).getRentalDate() + "\nReturn Date:" + map.get(mapped.getKey()).getReturnDate() + "\n\n");
            }
            bwr.close();
        } catch (Exception ioe) {
            System.out.println("Error");
        }
    }

    public void writeHistoricalReport(Map<String, List<Transactions>> map,String path){ // Writes historical report to txt file
        try {
            BufferedWriter bwr = new BufferedWriter(new FileWriter(path));
            Set setEntry = map.entrySet();
            Iterator iteration = setEntry.iterator();
            for (Map.Entry<String, List<Transactions>> entry : map.entrySet()) {
                String key = entry.getKey();
                List<Transactions> newList = map.get(key);
                for (Transactions t:newList){
                    bwr.write("Rental ID: "+t.getRentalId()+"\nDog ID: "+t.getDogId()+"\nCustomer Name: "+t.getCustomer()+"\nCustomer Email: "+t.getEmail()+"\nRental Date: "+t.getRentalDate()+"\nReturn Date: "+t.getReturnDate()+"\n\n");
                }
                bwr.write("Total Rentals for: "+key+": ["+map.get(key).size()+"]\n\n");
                bwr.write("-------------------------------------------");
            }
            bwr.close();
        } catch (Exception ioe) {
            System.out.println("Input output error");
        }
    }

    public void writeBreedReport(Map<String,Map<String, Map<String,List<Transactions>>>> map,String path) { // Writes report by breed then by month and by year with totals
        try{
            BufferedWriter bwr = new BufferedWriter(new FileWriter(path));
            Set setEntry = map.entrySet();
            Iterator iteration = setEntry.iterator();
            while (iteration.hasNext()) { // First iteration writes breed and iterates by breed
                int totalCount = 0;
                Map.Entry mapped = (Map.Entry) iteration.next();
                Set setEntryTwo = map.get(mapped.getKey()).entrySet();
                bwr.write("\nDog Breed: "+mapped.getKey()+"\n");
                Iterator iterationTwo = setEntryTwo.iterator();
                while (iterationTwo.hasNext()) { // Second iteration writes year and iterates by year
                    int yearCount = 0;
                    Map.Entry mappedTwo = (Map.Entry) iterationTwo.next();
                    bwr.write("\n\tYear: "+mappedTwo.getKey()+"\n");
                    Set setEntryThree = map.get(mapped.getKey()).get(mappedTwo.getKey()).entrySet();
                    Iterator iterationThree = setEntryThree.iterator();
                    while (iterationThree.hasNext()){ // Third iteration writes rentals by month and iterates by month
                        int monthCount = 0;
                        Map.Entry mappedThree = (Map.Entry) iterationThree.next();
                        bwr.write("\n\t\tMonth: "+mappedThree.getKey()+"\n\n");
                        for (Transactions t : map.get(mapped.getKey()).get(mappedTwo.getKey()).get(mappedThree.getKey())){ // Writes all transaction items stored in Month
                            bwr.write("\t\t\tRental ID: "+t.getRentalId()+"\n\t\t\tDog Name: "+t.getDogName()+"\n\t\t\tCustomer Name: "+t.getCustomer()+"\n\t\t\tCustomer Email: "+t.getEmail()+"\n\t\t\tRental Date: "+t.getRentalDate()+"\n\t\t\tReturn Date: "+t.getReturnDate()+"\n\t\t\tBreed: "+t.getBreed()+"\n");
                            monthCount++;
                        }
                        bwr.write("\n\t\tTotal Rentals for "+mappedThree.getKey()+" Month: "+monthCount+"\n\n");
                        yearCount = yearCount+monthCount;
                    }
                    bwr.write("\n\tTotal Rentals for "+mappedTwo.getKey()+" Year: "+yearCount+"\n\n");
                    totalCount = totalCount + yearCount;
                }
                bwr.write("\nTotal Rentals for "+mapped.getKey()+" Breed: "+totalCount);
                bwr.write("\n-------------------------------------------");
            }
            bwr.close();
        }catch (Exception ioe){
            System.out.println("Error");
        }
    }
}
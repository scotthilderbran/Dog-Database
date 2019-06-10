import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

// Report class to export Maps of report needed

public class Report {

    public HashMap<Integer, Transactions> pullReportIndividual(HashMap<Integer, Transactions> hashMap, String email) { // Individual report type method
        HashMap<Integer, Transactions> report = new HashMap<Integer, Transactions>();
        for (Map.Entry<Integer, Transactions> entry : hashMap.entrySet()) {
            int key = entry.getKey();
            if (hashMap.get(key).getEmail().equals(email)) {
                report.put(key, hashMap.get(key));
            } else {
            }
        }
        return report;
    }

    public Map<String, List<Transactions>> pullReportHistorical(HashMap<Integer, Transactions> hashMap) { // Historical report type method
        List<Transactions> transactionsList = new ArrayList<Transactions>(hashMap.values());
        Map<String, List<Transactions>> groupByEmailMap =
                transactionsList.stream().collect(Collectors.groupingBy(Transactions::getEmail));
        Set setEntry = groupByEmailMap.entrySet();
        Iterator iteration = setEntry.iterator();
        while (iteration.hasNext()) {
            Map.Entry mapped = (Map.Entry) iteration.next();
            Collections.sort(groupByEmailMap.get(mapped.getKey()), new Comparator<Transactions>() {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    public int compare(Transactions o1, Transactions o2) {
                        try{
                            Date d1 = df.parse(o1.getRentalDate());
                            Date d2 = df.parse(o2.getRentalDate());
                            return d1.compareTo(d2);
                        }catch (ParseException e){
                            System.out.println("Sorting parse error");
                            return 0;
                        }
                }
            });
        }
        return groupByEmailMap;
    }

   public Map<String,Map<String, Map<String,List<Transactions>>>> pullReportBreed(HashMap<Integer, Transactions>hashMap){ // Full breed report method including grouping by month and year
       Map<String,Map<String, Map<String,List<Transactions>>>> outputMap = new HashMap<>();
       List<Transactions> transactionsList = new ArrayList<Transactions>(hashMap.values());
       Map<String, List<Transactions>> groupByBreed =
               transactionsList.stream().collect(Collectors.groupingBy(Transactions::getBreed));
       Set setEntry = groupByBreed.entrySet();
       for (Map.Entry<String, List<Transactions>> entry : groupByBreed.entrySet()) {
           Map<String, Map<String,List<Transactions>>> yearlyOutput = new HashMap<>();
           String breedKey = entry.getKey();
           List<Transactions> listByYear = new ArrayList<Transactions>(groupByBreed.get(breedKey));
           Map<String, List<Transactions>> groupByYear= listByYear.stream().collect(Collectors.groupingBy(Transactions::getYear));
           for (Map.Entry<String, List<Transactions>> entryTwo : groupByYear.entrySet()) {
               String yearKey = entryTwo.getKey();
               List<Transactions> listByMonth = new ArrayList<Transactions>(groupByYear.get(yearKey));
               Map<String, List<Transactions>> groupByMonth= listByMonth.stream().collect(Collectors.groupingBy(Transactions::getMonth));
               yearlyOutput.put(yearKey,groupByMonth);
           }
           outputMap.put(breedKey,yearlyOutput);
       }
        return outputMap;
    }
}


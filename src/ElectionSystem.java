import java.util.*;
public class ElectionSystem {
    public static void main(String[] args) {
        Random random = new Random();
        LinkedList<String> l = new LinkedList<>();
        HashSet<String> s = new HashSet<>();
        String[] arr = {"Marcus Fenix", "Dominic Santiago", "Damon Baird", "Cole Train",
                "Anya Stroud", "Samantha Byrne", "Jace Stratton", "Augustus Cole", "Bernie Mizuki",
                "Adam Fenix", "Queen Myrrah", "Skip Chylar", "Locust Queen", "Jon Doe", "Ramon Salazar",
                "Oscar Calraca"};
        int numberOfCandidates = random.nextInt(2,16);
        // randomly selects a random number of candidates
        for (int i = 0; i < numberOfCandidates; i++) {
            int index = random.nextInt(arr.length);
            if(!s.contains(arr[index])) {
                l.add(arr[index]);
                s.add(arr[index]);
            } else {
                i--;
            }
        }
        // creates an election with a random number of people on the electorate
        Election e = new Election(random.nextInt(100) + 1);
        e.initializeCandidates(l);

        // randomly casts votes
        for(int i = 0; i < e.p; i++) {
            e.castRandomVote();
        }
        System.out.println(e.getTopKCandidates(3) + "\n");



        e.auditElection();
        System.out.println("\n");
        // rigs election for random candidate
        e.rigElection(s.toArray()[random.nextInt(s.size())].toString());
        e.auditElection();
        System.out.println("\n");
        System.out.println(e.getTopKCandidates(5));
    }
}

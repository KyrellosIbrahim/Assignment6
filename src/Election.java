import java.util.*;
public class Election {
    HashMap<String, Integer> votes = new HashMap<>();
    PriorityQueue<Map.Entry<String, Integer>> heap;
    int p; // electorate votes limit
    int voteCount; // current votes cast

    public Election(int p) {
        this.p = p;
        voteCount = 0;
        heap = new PriorityQueue<>();
    }

    /**
     * initialize the election system with the list of candidates given.
     * @param candidates the list of candidates given
     */
    public void initializeCandidates(LinkedList<String> candidates) {
        for (String candidate : candidates) {
            votes.put(candidate, 0);
        }
    }

    /**
     * simulate a vote for the specified candidate and update the priority queue.
     * @param candidate the candidate that will be voted for
     */
    public void castVote(String candidate) {
        if(voteCount+1 > p) {
            System.out.println("Error: cannot add another vote - maximum votes reached.");
            return;
        }
        voteCount++;
        votes.put(candidate, votes.get(candidate)+1);
        updatePriorityQueue();
    }

    /**
     * simulate a vote for a random candidate and update the priority queue.
     */
    public void castRandomVote() {
        Random random = new Random();
        int index = random.nextInt(votes.size());
        String candidate = (String) votes.keySet().toArray()[index];
        castVote(candidate);
        updatePriorityQueue();
    }

    /**
     * simulate enough votes for the given candidate to win the election and update the priority queue.
     * @param candidate the candidate in which to add votes for to win the election
     */
    public void rigElection(String candidate) {
        if(heap.isEmpty()) {
            return;
        }
        int highestVotes = heap.peek().getValue();
        int candidateVotes = votes.get(candidate);
        votes.put(candidate, highestVotes);
        votes.put(heap.peek().getKey(), candidateVotes);
        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            if(entry.getValue().equals(votes.get(candidate)) && !entry.getKey().equals(candidate)) {
                votes.put(candidate, votes.get(candidate)+1);
                votes.put(entry.getKey(), entry.getValue()-1);
            }
        }
        updatePriorityQueue();
    }

    /**
     * return the top k candidates with the most votes
     * @param k the number of candidates from the top
     * @return list of selected candidates
     */
    public LinkedList<String> getTopKCandidates(int k) {
        LinkedList<String> topCandidates = new LinkedList<>();
        if(k > heap.size()) {
            for(int i = 0; i < votes.size(); i++) {
                topCandidates.add(heap.poll().getKey());
            }
            updatePriorityQueue();
            return topCandidates;
        }
        for (int i = 0; i < k; i++) {
            topCandidates.add(heap.poll().getKey());
        }
        updatePriorityQueue();
        return topCandidates;
    }

    /**
     * print to console all the candidates and their votes in descending order.
     */
    public void auditElection() {
        for(int i = 0; i < votes.size(); i++) {
            Map.Entry<String, Integer> entry = heap.poll();
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votes");
        }
        updatePriorityQueue();
    }

    /**
     * helper method to update the queue after a vote has been cast.
     */
    public void updatePriorityQueue() {
        heap = new PriorityQueue<>(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        heap.addAll(votes.entrySet());
    }
}

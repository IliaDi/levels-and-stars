import java.io.File;
import java.io.IOException;
import java.util.*;

class Pista {
    private int pista_number;
    private int required_keys;
    private int bonus_keys;
    private long stars;
    private ArrayList<Integer> rkey_codes;
    private ArrayList<Integer> bkey_codes;

    public Pista(int number, int k, int r, long s, ArrayList<Integer> kcodes, ArrayList<Integer> rcodes) {
        pista_number = number;
        required_keys = k;
        bonus_keys = r;
        stars = s;
        rkey_codes = new ArrayList<>(required_keys);
        rkey_codes.addAll(kcodes);
        bkey_codes = new ArrayList<>(bonus_keys);
        bkey_codes.addAll(rcodes);

    }

    public Pista() {

    }

    public int getPista_number() {
        return pista_number;
    }

    public int getRequired_keys() {
        return required_keys;
    }

    public int getBonus_keys() {
        return bonus_keys;
    }

    public long getStars() {
        return stars;
    }

    public ArrayList<Integer> getRkey_codes() {
        return rkey_codes;
    }

    public ArrayList<Integer> getBkey_codes() {
        return bkey_codes;
    }

}

class Node {

    private long gathered_stars;
    private int pista_number;
    private HashMap gathered_keys;
    private HashMap visited_pistes;

    public Node(long s, int n, HashMap k, HashMap v) {
        gathered_keys = new HashMap();
        visited_pistes = new HashMap();
        gathered_keys.putAll(k);
        pista_number = n;
        gathered_stars = s;
        visited_pistes.putAll(v);
    }


    public HashMap getVisited_pistes() {
        return visited_pistes;
    }

    public long getGathered_stars() {
        return gathered_stars;
    }

    public HashMap getGathered_keys() {
        return gathered_keys;
    }

}


public class Pistes {

    public static void main(String[] args) {

        File game = new File(args[0]);
        int N = 0;
        int k, r, i, j;
        long s;
        ArrayList<Integer> A = new ArrayList<>();
        ArrayList<Integer> B = new ArrayList<>();
        ArrayList<Pista> pistes = new ArrayList<>();
        try {
            Scanner sc = new Scanner(game);
            N = sc.nextInt();
            for (i = 0; i <= N; i++) {
                A.clear();
                B.clear();
                k = sc.nextInt();
                r = sc.nextInt();
                s = sc.nextInt();
                for (j = 0; j < k; j++) {
                    int a = sc.nextInt();
                    A.add(j, a);
                }
                for (j = 0; j < r; j++) {
                    int a = sc.nextInt();
                    B.add(j, a);
                }
                Pista p = new Pista(i, k, r, s, A, B);
                pistes.add(i, p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Deque<Node> deque_next_pista = new ArrayDeque<Node>();
        HashMap visited = new HashMap<Integer, Integer>();

        //key = key code ,value=number of these keys
        HashMap keys = new HashMap<Integer, Integer>();

        Pista first = new Pista();
        first = (Pista) pistes.get(0);
        for (i = 0; i < first.getBonus_keys(); i++) {
            if (keys.containsKey(first.getBkey_codes().get(i))) {
                Integer x = (Integer) keys.get(first.getBkey_codes().get(i));
                keys.put(first.getBkey_codes().get(i), x + 1);
            } else {
                keys.put(first.getBkey_codes().get(i), 1);
            }
        }
        long counting_stars = first.getStars();
        visited.put(first.getPista_number(), first.getPista_number());
        long max_stars = counting_stars;
        HashMap my_keys = new HashMap<Integer, Integer>();
        HashMap visited_n = new HashMap<Integer, Integer>();
        deque_next_pista.add(new Node(first.getStars(), first.getPista_number(), keys, visited));

        while (deque_next_pista.peek() != null) {
            boolean flag1 = true;
            Node current = deque_next_pista.remove();
            keys.clear();
            visited.clear();
            keys.putAll(current.getGathered_keys());
            visited.putAll(current.getVisited_pistes());
            counting_stars = current.getGathered_stars();

            for (i = 1; i <= N; i++) {
                Pista me = pistes.get(i);
                boolean flag = true;
                my_keys.clear();
                my_keys.putAll(keys);
                if (!visited.containsKey(me.getPista_number())) {
                    for (j = 0; j < me.getRequired_keys(); j++) {
                        if (!my_keys.containsKey(me.getRkey_codes().get(j))) {
                            flag = false;
                        } else {
                            Integer x = (Integer) my_keys.get(me.getRkey_codes().get(j));
                            x = x - 1;
                            if (x == 0) {
                                my_keys.remove(me.getRkey_codes().get(j));
                            } else {
                                my_keys.put(me.getRkey_codes().get(j), x);
                            }

                        }
                    }
                    my_keys.clear();
                    my_keys.putAll(keys);
                    //if we have all the keys for this pista and it hasn't been visited before,//MPOREI NA EXW KLEIDI LLA OXI ARKETES FORES
                    if (flag) {
                        for (j = 0; j < me.getRequired_keys(); j++) {
                            Integer x = (Integer) my_keys.get(me.getRkey_codes().get(j));
                            x = x - 1;
                            if (x == 0) {
                                my_keys.remove(me.getRkey_codes().get(j));
                            } else {
                                my_keys.put(me.getRkey_codes().get(j), x);
                            }
                        }
                        for (j = 0; j < me.getBonus_keys(); j++) {
                            if (my_keys.containsKey(me.getBkey_codes().get(j))) {
                                Integer x = (Integer) my_keys.get(me.getBkey_codes().get(j));
                                my_keys.put(me.getBkey_codes().get(j), x + 1);
                            }
                            my_keys.put(me.getBkey_codes().get(j), 1);
                        }
                        visited_n.clear();
                        visited_n.putAll(visited);
                        visited_n.put(me.getPista_number(), me.getPista_number());
                        deque_next_pista.add(new Node(counting_stars + me.getStars(), me.getPista_number(), my_keys, visited_n));
                        flag1 = false; //found a possible next game/pista
                    }
                }

            }
            //if path is over
            if (keys.isEmpty() || flag1) {
                if (counting_stars > max_stars) {
                    max_stars = counting_stars;
                }
            }
        }
        System.out.print(max_stars);
    }
}


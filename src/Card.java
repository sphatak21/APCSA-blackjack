
public class Card {
    private String rank;
    private String suit;
    public Card (String rank, String suit){
        this.rank = rank;
        this.suit = suit;
    }
    public String getRank(){
        return rank;
    }
    public String getSuit(){
        return suit;
    }
    public static int[] getOrderedRank(String rank){
        int [] a = new int[2];
        try {
             a[0] = Integer.parseInt(rank);
        } catch (NumberFormatException e){
            if(rank.equals("A")){
                a[0] = 1;
                a[0] = 11;
            }else {
                a[0] = 10;
            }
        }
        return a;
    }
    @Override
    public String toString() {
        if(rank == null || suit == null){
            return "Face Down Card";
        }else{
            return rank + suit;

        }
    }

}

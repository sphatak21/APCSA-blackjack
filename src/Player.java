import java.util.*;
public class Player {
    private ArrayList<Card> hand;
    private long chips;
    public Player(long chips){
        this.hand = new ArrayList<>();
        this.chips = chips;
    }
    public ArrayList <Card> getMaskedHand(){
        ArrayList<Card> handShown = new ArrayList<>();
        for(Card i : getHand()){
            handShown.add(i);
        }
        handShown.remove(handShown.size() - 1);
        handShown.add(new Card(null, null));
        return handShown;
    }
    public ArrayList <Card> getHand(){
        return hand;
    }
    public void takeCard(Card card){
        hand.add(card);
    }
    public void showHand(boolean showAllCards, boolean player){
        String message = "CPU's Hand";
        if(player){
            message = "Your Hand";
        }
        System.out.println(message);
        if(showAllCards){
            System.out.println(getHand());
        }else{
            System.out.println(getMaskedHand());
        }
    }
    public long getChips(){
        return chips;
    }
    public int[] checkHand(){
        int value = 0;
        for(Card i : hand){
            value += i.getOrderedRank(i.getRank())[0];
        }
        int[] values = new int[2];
        if(value > 21) {
            for(Card i : hand){
                if (i.getRank().equals("A") && value>21) {
                    value -= 10;
                }
            }
        }if (value < 21){
            values[0] = -1;

        }else if (value == 21){
            values[0] = 0;
        }else {
            values[0] = 1;
        }
        values[1] = value;
        return values;
    }

    public void setChips(long chips) {
        this.chips = chips;
    }

    public void emptyHand() {
        this.hand = new ArrayList<Card>();
    }
}

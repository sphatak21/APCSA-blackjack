import java.util.*;
public class BlackJack {

    private final String[] SUITS = { "C", "D", "H", "S" };
    private final String[] RANKS = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K" };

    private final Player player;
    private final Player computer;
    private List<Card> deck;
    private final Scanner in;
    public BlackJack(long n) {
        this.player = new Player(n);
        this.computer = new Player(Integer.MAX_VALUE);
        this.in = new Scanner(System.in);
    }
    public void play() {
        long wager;
        boolean startGame = true;
        while (true){
            if(startGame){
                initializeDeck();
                computer.emptyHand();
                player.emptyHand();
                shuffleAndDeal();
                startGame=false;
            }

            System.out.println("Chip Balance: $" + player.getChips());
            System.out.print("How much would you like to wager? $");
            String input = in.nextLine();
            checkQuit(input);
            wager = checkValidInput(input);
            if(wager > player.getChips() || wager < 0){
                System.out.println("Invalid number of chips");
            }else{
                int playerstatus = takeTurn(false);
                int computerstatus = takeTurn(true);
                int playerValue = player.checkHand()[1];
                int computerValue = computer.checkHand()[1];

                player.showHand(true, true);
                computer.showHand(true, false);
                if(playerstatus > 0){
                    showmsg("lose");
                    player.setChips(player.getChips()-wager);
                }else if(playerstatus < 0){
                    if(playerValue > computerValue){
                        showmsg("win");
                        player.setChips(player.getChips() + wager);
                    }else if(playerValue < computerValue){
                        if(computerValue <= 21){
                            showmsg("lose");
                            player.setChips(player.getChips() - wager);
                        }else {
                            showmsg("win");
                            player.setChips(player.getChips() + wager);
                        }
                    }else {
                        showmsg("push");
                    }
                }else{
                    if(computerstatus != 0){
                        if(player.getHand().size() == 2){
                            player.setChips(player.getChips() + (int)(wager * 1.5));
                            showmsg("blackjack");
                        }else{
                            player.setChips(player.getChips() + wager);
                            showmsg("win");
                        }
                    }else{
                        showmsg("push");
                    }
                }
                startGame = true;
            }
        }
    }
    public void shuffleAndDeal(){
        if(deck == null){
            initializeDeck();
        }
        Collections.shuffle(deck);
        while(player.getHand().size() < 2){
            player.takeCard(deck.remove(0));
            computer.takeCard(deck.remove(0));
        }
    }
    private void initializeDeck(){
        deck = new ArrayList<>(52);
        for(String suit : SUITS){
            for(String rank : RANKS){
                deck.add(new Card(rank, suit));
            }
        }
    }
    private int takeTurn(boolean cpu){
        if(!cpu){
            while(true){
                int[] values = player.checkHand();
                player.showHand(true, true);
                computer.showHand(false, false);
                System.out.print("\nHit or Stand? ");
                String res = in.nextLine().toUpperCase();
                checkQuit(res);
                if(res.equals("HIT") || res.equals("H")){
                    player.takeCard(deck.remove(0));
                    if(player.checkHand()[1] > 21){
                        return 1;
                    }
                }else if(res.equals("STAND") || res.equals("S")){
                    return values[0];
                }else {
                    System.out.println("Invalid Command");
                }
            }
        }else {
            while(true){
                player.getHand();
                int[] values = computer.checkHand();
                int[] playerValues = player.checkHand();
                if(values[1] < 17 && playerValues[1] > values[1]){
                    computer.takeCard(deck.remove(0));
                }else if(values[1] >= 17 || values[1] > playerValues[1]){
                    return values[0];
                }
            }

        }

    }
    public static void main(String[] args) {
        System.out.println("##############################################################################");
        System.out.println("#                                                                            #");
        System.out.println("#  ######  #           #      #####  #    #       #      #      ####  #   #  #");
        System.out.println("#  #     # #          # #    #       #   #        #     # #    #      #  #   #");
        System.out.println("#  ######  #         #   #   #       # #          #    #   #   #      ##     #");
        System.out.println("#  #     # #        # # # #  #       #   #   #    #   # # # #  #      #  #   #");
        System.out.println("#  ######  ####### #       #  #####  #    #   ####   #       #  ####  #   #  #");
        System.out.println("#                                                                            #");
        System.out.println("#  A human v. CPU rendition of the classic card game                         #");
        System.out.println("#  Blackjack.                                                                #");
        System.out.println("#                                                                            #");
        System.out.println("##############################################################################");

        long chips = startGame();
        BlackJack game = new BlackJack(chips);
        game.play();
    }
    private static long startGame(){
        long a;
        while(true){
            Scanner init = new Scanner(System.in);
            System.out.print("Enter the number of chips you would like to buy: ");
            String input = init.nextLine();
            checkQuit(input);
            a = checkValidInput(input);

            if(a > 0){
                return a;
            }
        }

    }
    private static void checkQuit (String s){
        if(s.toUpperCase().equals("QUIT") || s.toUpperCase().equals("Q")){
            System.exit(0);
        }
    }
    private static long checkValidInput (String input){
        long a;
        try {
            a = Long.parseLong(input);
            if(a > 0){
                return a;
            }else {
                return -1;
            }

        }catch (NumberFormatException e){
           return -1;
        }
    }
    private void showmsg(String win){
        if(win.toUpperCase().equals("WIN")){
            System.out.println("You have won this round!");
        }else if (win.toUpperCase().equals("LOSE")){
            System.out.println("You have lost this round, better luck next time!");
        }else if(win.toUpperCase().equals("PUSH")){
            System.out.println("You have tied this round.");
        }else if(win.toUpperCase().equals("BLACKJACK")){
            System.out.println("Blackjack!");
        }
    }







}

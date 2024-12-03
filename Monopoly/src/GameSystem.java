import java.util.ArrayList;
import java.util.List;

public class GameSystem {
    private final Land[] LANDS;
    private final Player[] players;
    private boolean gameOver=false;
    private int Current=-1;

    public GameSystem(Player[] players, Land[] lands) {
        this.players = players;
        this.LANDS = lands;
        this.gameOver=false;
    }

    public Player nextPlayer() {
        int playerNum=players.length;
        for( int i=0 ; i < playerNum ; i++ ) {
            Current++;
            Current = Current % players.length;
            if(players[Current].isActive())
                return players[Current];
        }
        return null;
    }

    public Player getCurrentPlayer() {
        if(Current==-1 || players.length==0 )
            return null;
        else
            return players[Current];
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean dealFailedPlayer(){
        if(!players[Current].isActive()){
            for( Land land:LANDS){
                if(land.getHouse()!=null && land.getHouse().getPlayer()==players[Current]){
                    land.setHouse(null);
                }
            }
            int activePlayers=0;
            for( Player player : players){
                if(player.isActive()){
                    activePlayers++;
                }
            }
            if (activePlayers <= 1) {
                gameOver = true;
            } else {
                gameOver = false;
            }
            return true;
        }
        return false;
    }

    public String[] currentPlayersState(){
        String[] states=new String[players.length];
        for(int i=0;i<players.length;i++){
            states[i]=players[i].toString();
        }
        return states;
    }

    public String[] currentLandsState(){
        List<String> landStates=new ArrayList<String>();
        for(Land land:LANDS){
            if(land.getHouse()!=null){
                landStates.add(land.toString());
            }
        }
        String[] result = new String[landStates.size()];
        return landStates.toArray(result);
    }


    public boolean isColorOccupied(LandColor color , Player currentPlayer){
        for(Land land:LANDS){
            if(land.getColor()==color && land.getHouse()!=null && !land.getHouse().getPlayer().equals(currentPlayer)){
                return true;
            }
        }
        return false;
    }


    public void nextTurn(int step,int cost){
        Player currentPlayer= getCurrentPlayer();;
        if (currentPlayer == null)
            currentPlayer = nextPlayer();
        if(!currentPlayer.isActive())
            return;

        int targetLocation = (currentPlayer.getLocation()+step)%LANDS.length;
        currentPlayer.setLocation(targetLocation);
        Land targetLand =LANDS[targetLocation];

        if(targetLand.getHouse()!=null){
            House house= targetLand.getHouse();
            if(house.getPlayer()!=currentPlayer){
                double rent= currentPlayer.payRent(house.getHousePrice());
                house.getPlayer().collectRent(rent);
                dealFailedPlayer();
            }
        }
        else{
            if(cost>0 && currentPlayer.getMoney()>=cost ){
                LandColor targetColor = targetLand.getColor();
                boolean colorOccupied = false;

                for(Land land:LANDS){
                    if(land.getColor()==targetColor && land.getHouse()!=null && land.getHouse().getPlayer()!=currentPlayer){
                        colorOccupied = true;
                        break;
                    }
                }

                if(!colorOccupied){
                    boolean builtHouse = currentPlayer.buildHouse(targetLand,cost);
                    if(builtHouse){
                        System.out.println("House built on "+ targetLand);
                    }
                }
            }
        }

        nextPlayer();
    }

}

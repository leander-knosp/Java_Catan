import de.dhbw.catan.model.ResourceType;
import de.dhbw.catan.model.Player;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Trade {

    private List<Player> allPlayers;
    private Map<ResourceType, Integer> offerFromYou;
    private Map<ResourceType, Integer> requestFromOther;
    private String targetPlayerColor;

    public Trade(Map<ResourceType, Integer> offerFromYou,
                    Map<ResourceType, Integer> requestFromOther,
                    String targetPlayerColor) {
        this.offerFromYou = offerFromYou;
        this.requestFromOther = requestFromOther;
        this.targetPlayerColor = targetPlayerColor;
    }

    public bool handleTrade(){
        for (Player player : allPlayers) {
            if (player.getColor().equals(targetPlayerColor) && !player.equals(currentPlayer)) {
                Player targetPlayer = player;
    
                if (canTrade(currentPlayer, targetPlayer)) {
                    executeTrade(currentPlayer, targetPlayer);
                    return true;
                } else {
                    System.out.println("Handel nicht möglich – Ressourcen fehlen.");
                    return false;
                }
            }
        }
    }

    private void executeTrade(Player currentPlayer, Player targetPlayer) {
        for (Map.Entry<ResourceType, Integer> entry : offerFromYou.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            currentPlayer.addResource(resourceType, -amount);
        }

        for (Map.Entry<ResourceType, Integer> entry : requestFromOther.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            currentPlayer.addResource(resourceType, amount);
        }

        for (Map.Entry<ResourceType, Integer> entry : requestFromOther.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            targetPlayer.addResource(resourceType, -amount);
        }

        for (Map.Entry<ResourceType, Integer> entry : offerFromYou.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            targetPlayer.addResource(resourceType, amount);
        }
    }

    private boolean canTrade(Player currentPlayer, Player targetPlayer) {
        for (Map.Entry<ResourceType, Integer> entry : offerFromYou.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            if (currentPlayer.getResourceCount(resourceType) < amount) {
                return false;
            }
        }

        for (Map.Entry<ResourceType, Integer> entry : requestFromOther.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            if (targetPlayer.getResourceCount(resourceType) < amount) {
                return false;
            }
        }

        return true;
    }
}

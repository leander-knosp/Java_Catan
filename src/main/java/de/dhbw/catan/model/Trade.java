import de.dhbw.catan.model.ResourceType;
import lombok.Data;

@Data
public class Trade {

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

    }
}

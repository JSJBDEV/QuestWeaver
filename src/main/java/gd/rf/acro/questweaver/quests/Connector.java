package gd.rf.acro.questweaver.quests;

import net.minecraft.util.Identifier;


public class Connector {
    private Identifier type;
    private String requirements;
    private int x;
    private int y;
    private String cat;
    public Connector(String identifier, int xloc, int yloc, String req, String category)
    {
        String[] split = identifier.split(":");
        this.type=new Identifier(split[0],split[1]);
        this.x=xloc;
        this.y=yloc;
        this.requirements=req;
        this.cat=category;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String getRequirements() {
        return requirements;
    }

    public Identifier getType() {
        return type;
    }

    public String getCat() {
        return cat;
    }
}

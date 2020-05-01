package gd.rf.acro.questweaver.quests;


import net.minecraft.util.Identifier;

import java.util.Arrays;

public class Quest {
    private String name;
    private String text;
    private String condition;
    private String args;
    private Identifier icon;
    private String hover;
    private int x;
    private int y;
    private String req;
    private String cat;
    public Quest(String questName,String textIn, String questCondition, String questConArgsCompound,String questIconName, String hovertext,int xloc, int yloc, String requirements, String category)
    {
        this.args=questConArgsCompound;
        this.name=questName;
        this.condition=questCondition;
        String[] full = questIconName.split(":");
        this.icon=new Identifier(full[0],full[1]);
        this.hover=hovertext;
        this.text=textIn;
        this.x=xloc;
        this.y=yloc;
        this.req=requirements;
        this.cat=category;
    }


    public String getName() {
        return name;
    }

    public Identifier getIcon() {
        return icon;
    }

    public String getArgs() {
        return args;
    }

    public String getCondition() {
        return condition;
    }

    public String getHover() {
        return hover;
    }

    public String getText() {
        return text;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getReq() {
        return req;
    }

    public String getCat() {
        return cat;
    }
}

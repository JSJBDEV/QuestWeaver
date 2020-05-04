package gd.rf.acro.questweaver.screens;

import gd.rf.acro.questweaver.QuestWeaver;
import gd.rf.acro.questweaver.Utils;
import gd.rf.acro.questweaver.quests.Connector;
import gd.rf.acro.questweaver.quests.Quest;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Set;

public class QuestScreen extends Screen {
    private static int twidht;
    private static final Identifier BOOKSPREAD = new Identifier("questweaver","textures/gui/book2.png");


    private static Set<String> reqTags;
    private static String cat;
    public QuestScreen(Text title, Set<String> tags, String category) {
        super(title);
        reqTags=tags;
        cat=category;
    }
    private static void test()
    {


    }

    @Override
    protected void init() {
        twidht=width;
        super.init();
        int i = (twidht-315)/2;
        List<String> iv = QuestWeaver.entryList.getCategories();
        this.addButton(new ButtonWidget(i+276,156, 20, 20,">",(button)->
        {

            String next;
            if(iv.indexOf(cat)+1==iv.size())
            {
                next=iv.get(0);
            }
            else
            {
                next=iv.get(iv.indexOf(cat)+1);
            }
            MinecraftClient.getInstance().openScreen(new QuestScreen(new LiteralText("quests"),reqTags,next));
        }));


        this.addButton(new ButtonWidget(i+251,156, 20, 20,"<",(button)->
        {
            String next;
            if(iv.indexOf(cat)-1==-1)
            {
                next=iv.get(iv.size()-1);
            }
            else
            {
                next=iv.get(iv.indexOf(cat)-1);
            }
            MinecraftClient.getInstance().openScreen(new QuestScreen(new LiteralText("quests"),reqTags,next));
        }));
        makeIconButtons();
        makeConnectorButtons();


    }
    public static void drawBackground(int mouseX, int mouseY, float partialTicks)
    {
        int i = (twidht-315)/2;
        drawFromTexture(BOOKSPREAD,i,0,0,0,315,175);

    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        twidht=width;
        int i = (twidht-315)/2;
        renderBackground();
        drawBackground(mouseX,mouseY,delta);
        this.drawString(this.font,cat,i+18,5, 0xFF00FFFF);
        super.render(mouseX,mouseY,delta);
        buttons.forEach(button->
        {
            if(button.isHovered())
            {
                this.drawString(this.font,button.getMessage(),mouseX+5,mouseY+5, 0xFF00FFFF);
            }
        });

    }

    @Override
    public void tick() {
        super.tick();
    }

    public static void drawFromTexture(Identifier resourceLocation, int x, int y, int u, int v, int w, int h)
    {
        MinecraftClient.getInstance().getTextureManager().bindTexture(resourceLocation);

        blit(x, y, u, v, w, h, 512, 256);
    }

    public TexturedButtonWidget makeIconButton(Quest quest, int x, int y)
    {
        TexturedButtonWidget button =  this.addButton(new TexturedButtonWidget(x, y, 20, 20, 0, 0, 0,
                quest.getIcon(), 20, 20, (p_213088_1_) ->
        {
            Utils.sendToServer(quest.getName(),quest.getCondition(),quest.getArgs());
            this.minecraft.openScreen(new QuestPageScreen(new LiteralText("book"),quest,reqTags,cat));

        }));
        button.setMessage(quest.getHover());
        return button;

    }
    public TexturedButtonWidget makeConnector(Connector connector, int x, int y)
    {
        return this.addButton(new TexturedButtonWidget(x, y, 20, 20, 0, 0, 0,
               connector.getType(), 20, 20, (p_213088_1_) ->
        { }));

    }
    public void makeIconButtons()
    {
        int i = (twidht-315)/2;
        List<Quest> quests = QuestWeaver.entryList.getQuests();
        for(Quest quest: quests)
        {
            System.out.println(reqTags.toString());
            if(reqTags.contains(quest.getReq()) || quest.getReq().equals("nil"))
            {
                if(cat.equals(quest.getCat()))
                {
                    int x = i+29+quest.getX()*25;
                    int y = 24+quest.getY()*25;
                    makeIconButton(quest,x,y);
                    if(reqTags.contains(quest.getName()))
                    {
                        this.addButton(new TexturedButtonWidget(x, y, 20, 20, 0, 0, 0,
                                new Identifier("questweaver","textures/gui/arrows/tick.png"), 20, 20, (p_213088_1_) ->
                        { }));
                    }
                }
            }

        }
    }
    public void makeConnectorButtons()
    {
        int i = (twidht-315)/2;
        List<Connector> connectors = QuestWeaver.entryList.getConnectors();
        for(Connector connector: connectors)
        {
            System.out.println(reqTags.toString());
            if(reqTags.contains(connector.getRequirements()) || connector.getRequirements().equals("nil"))
            {
                if(cat.equals(connector.getCat()))
                {
                    int x = i+29+connector.getX()*25;
                    int y = 24+connector.getY()*25;
                    makeConnector(connector,x,y);
                }
            }

        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}

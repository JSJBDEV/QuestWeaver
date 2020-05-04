package gd.rf.acro.questweaver.screens;

import gd.rf.acro.questweaver.Utils;
import gd.rf.acro.questweaver.quests.Quest;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Set;

public class QuestPageScreen extends Screen {
    private String text;
    private static int twidht;
    private Set<String> reqTags;
    private String category;
    private static final Identifier BOOKSPREAD = new Identifier("questweaver","textures/gui/book2.png");

    protected QuestPageScreen(Text titleIn, Quest quest, Set<String> tags, String cat) {
        super(titleIn);
        this.text= Utils.wrapString(quest.getText());
        this.category=cat;
        this.reqTags=tags;

    }
    public static void drawFromTexture(Identifier resourceLocation, int x, int y, int u, int v, int w, int h)
    {
        MinecraftClient.getInstance().getTextureManager().bindTexture(resourceLocation);

        blit(x, y, u, v, w, h, 512, 256);
    }
    public static void drawBackground(int mouseX, int mouseY, float partialTicks)
    {
        int i = (twidht-315)/2;
        drawFromTexture(BOOKSPREAD,i,0,0,0,315,175);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        int modif = (twidht-315)/2;
        int x = modif+25;
        renderBackground();
        drawBackground(mouseX,mouseY,partialTicks);
        String[] lines = text.split(";n");
        for (int i = 0; i < lines.length; i++) {
            int y = 24+i*10;
            this.drawString(font, lines[i],x,y, 0xFFFFFFFF);
        }


        twidht=width;
        super.render(mouseX,mouseY,partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    protected void init() {
        twidht=width;
        int i = (twidht-315)/2;
        this.addButton(new ButtonWidget(i+276,156, 20, 20,"<<",(button)->
        {
            MinecraftClient.getInstance().openScreen(new QuestScreen(new LiteralText("quests"),reqTags,category));
        }));
    }
}

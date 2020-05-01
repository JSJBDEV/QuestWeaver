package gd.rf.acro.questweaver;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void sendToServer(String name,String data,String args)
    {
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        String full = name+";;"+data+";;"+args;
        passedData.writeString(full);
        ClientSidePacketRegistry.INSTANCE.sendToServer(QuestWeaver.SEND_BOOK_PROCESS,passedData);
    }

    public static void reduceItem(PlayerEntity player, Item item, int amount)
    {
        if(player.inventory.countInInv(item)>=amount)
        {
            int amountCopy=amount;
            for (int i = 0; i < player.inventory.getInvSize(); i++) {
                ItemStack inv = player.inventory.getInvStack(i);
                if(inv.getItem()==item)
                {
                    if(inv.getCount()==amountCopy)
                    {
                        player.inventory.setInvStack(i,ItemStack.EMPTY);
                        player.inventory.markDirty();
                        return;
                    }
                    if(inv.getCount()>amountCopy)
                    {
                        inv.decrement(amountCopy);
                        player.inventory.markDirty();
                        return;
                    }
                    player.inventory.setInvStack(i,ItemStack.EMPTY);
                    amountCopy-=inv.getCount();
                    player.inventory.markDirty();
                }
            }
        }
    }

    public static String wrapString(String torap)
    {
        List<String> builder = new ArrayList<>();
        String[] array = torap.split(" ");
        for(String part:array)
        {
            builder.add(part);
            System.out.println(part);
        }
        for (int i = 0; i < builder.size(); i+=10) {
            builder.add(i,";n");
        }
        return StringUtils.join(builder," ");
    }

    public static Item getItem(String item)
    {
        System.out.println(item);
        String[] vv = item.split(":");
        return Registry.ITEM.get(new Identifier(vv[0],vv[1]));
    }
}

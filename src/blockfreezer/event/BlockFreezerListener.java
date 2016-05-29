package blockfreezer.event;

import blockfreezer.BlockFreezer;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;

public class BlockFreezerListener implements Listener{
    private BlockFreezer plugin;
    public BlockFreezerListener(BlockFreezer plugin){
        this.plugin = plugin;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockUpdate(BlockUpdateEvent event){
        if(this.plugin.isFreezable(event.getBlock())){
            event.setCancelled(true);
        }
    }
}
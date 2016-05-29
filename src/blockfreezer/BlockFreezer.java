package blockfreezer;

import blockfreezer.command.BlockFreezerCommand;
import blockfreezer.event.BlockFreezerListener;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import java.io.File;
import java.util.HashMap;

public class BlockFreezer extends PluginBase{
    private HashMap<String, Config> configs;
    @Override
    public void onEnable(){
        this.getDataFolder().mkdirs();
        this.configs = new HashMap<String, Config>();
        for(Level level : this.getServer().getLevels().values()){
            String ilevel = level.getName().toLowerCase();
            this.configs.put(ilevel, new Config(new File(this.getDataFolder(), ilevel+".txt"), Config.ENUM));
        }
        this.getServer().getCommandMap().register("blockfreezer", new BlockFreezerCommand(this));
        this.getServer().getPluginManager().registerEvents(new BlockFreezerListener(this), this);
    }
    public boolean addBlock(int id, int damage, String level){
        level = level.toLowerCase();
        if(this.configs.containsKey(level)){
            this.configs.get(level).set(id+":"+damage, null);
            this.configs.get(level).save();
            return true;
        }
        return false;
    }
    public boolean removeBlock(int id, int damage, String level){
        level = level.toLowerCase();
        if(this.configs.containsKey(level)){
            String key = id+":"+damage;
            if(this.configs.get(level).exists(key)){
                this.configs.get(level).remove(key);
                this.configs.get(level).save();
                return true;
            }
        }
        return false;
    }
    public boolean isFreezable(Block block){
        String ilevel = block.getLevel().getName().toLowerCase();
        if(this.configs.containsKey(ilevel)){
            return this.configs.get(ilevel).exists(block.getId()+":"+block.getDamage(), true);
        }
        return false;
    }
}
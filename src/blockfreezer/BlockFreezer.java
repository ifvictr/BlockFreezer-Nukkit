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
        getDataFolder().mkdirs();
        this.configs = new HashMap<>();
        for(Level level : getServer().getLevels().values()){
            String ilevel = level.getName().toLowerCase();
            configs.put(ilevel, new Config(new File(getDataFolder(), ilevel+".txt"), Config.ENUM));
        }
        getServer().getCommandMap().register("blockfreezer", new BlockFreezerCommand(this));
        getServer().getPluginManager().registerEvents(new BlockFreezerListener(this), this);
    }
    public boolean addBlock(int id, int damage, String level){
        level = level.toLowerCase();
        if(configs.containsKey(level)){
            configs.get(level).set(id+":"+damage, null);
            configs.get(level).save();
            return true;
        }
        return false;
    }
    public boolean removeBlock(int id, int damage, String level){
        level = level.toLowerCase();
        if(configs.containsKey(level)){
            String key = id+":"+damage;
            if(configs.get(level).exists(key)){
                configs.get(level).remove(key);
                configs.get(level).save();
                return true;
            }
        }
        return false;
    }
    public boolean isFreezable(Block block){
        String ilevel = block.getLevel().getName().toLowerCase();
        if(configs.containsKey(ilevel)){
            return configs.get(ilevel).exists(block.getId()+":"+block.getDamage(), true);
        }
        return false;
    }
}
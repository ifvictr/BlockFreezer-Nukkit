package blockfreezer.command;

import blockfreezer.BlockFreezer;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import java.util.HashMap;

public class BlockFreezerCommand extends Command{
    private BlockFreezer plugin;
    public BlockFreezerCommand(BlockFreezer plugin){
        super("blockfreezer", "Shows all BlockFreezer commands", null, new String[]{"bf"});
        setPermission("blockfreezer.command.blockfreezer");
        this.plugin = plugin;
    }
    private void sendCommandHelp(CommandSender sender){
        HashMap<String, String> commands = new HashMap<String, String>(){{
            put("addblock", "Adds a block type to the specified world");
            //put("blocks", "Lists all the freezable block types for the specified world");
            put("delblock", "Removes a block type to the specified world");
            put("help", "Shows all BlockFreezer commands");
        }};
        sender.sendMessage("BlockFreezer commands:");
        for(String name : commands.keySet()){
            sender.sendMessage("/blockfreezer "+name+": "+commands.get(name));
        }
    }
    @Override
    public boolean execute(CommandSender sender, String label, String[] args){
        if(!testPermission(sender)){
            return false;
        }
        if(args.length != 0){
            switch(args[0].toLowerCase()){
                case "a":
                case "addblock":
                    if(args.length == 4){
                        if(plugin.addBlock(Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3])){
                            sender.sendMessage(TextFormat.GREEN+"Successfully added "+args[1]+":"+args[2]+" to "+args[3]+".");
                        }
                        else{
                            sender.sendMessage(TextFormat.RED+"Failed to add.");
                        }
                    }
                    else{
                        sender.sendMessage(TextFormat.RED+"Please specify an id, damage value, and world name.");
                    }
                    break;
                /*
                case "b":
                case "blocks":
                    if(args.length == 2){
                        //TODO: Add this part in v1.1.0
                    }
                    else{
                        sender.sendMessage(TextFormat.RED+"Please specify a world name.");
                    }
                    break;
                 */
                case "d":
                case "delblock":
                    if(args.length == 4){
                        if(plugin.removeBlock(Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3])){
                            sender.sendMessage(TextFormat.GREEN+"Successfully removed "+args[1]+":"+args[2]+" from "+args[3]+".");
                        }
                        else{
                            sender.sendMessage(TextFormat.RED+"Failed to remove.");
                        }
                    }
                    else{
                        sender.sendMessage(TextFormat.RED+"Please specify an id, damage value, and world name.");
                    }
                    break;
                case "help":
                    sendCommandHelp(sender);
                    break;
                default:
                    sender.sendMessage("Usage: /blockfreezer <sub-command> [parameters]");
                    break;
            }
        }
        else{
            sendCommandHelp(sender);
            return false;
        }
        return true;
    }
}
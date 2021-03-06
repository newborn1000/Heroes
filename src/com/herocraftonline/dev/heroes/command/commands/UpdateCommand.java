package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.util.Updater;

public class UpdateCommand extends BaseCommand {

    public UpdateCommand(Heroes plugin) {
        super(plugin);
        name = "Update";
        description = "Updates the plugin";
        usage = "/hero admin update";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("hero admin update");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (!Heroes.Permissions.has((Player) sender, "heroes.admin.update")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to do this");
                return;
            }
            Updater.updateLatest();
        }

    }
}

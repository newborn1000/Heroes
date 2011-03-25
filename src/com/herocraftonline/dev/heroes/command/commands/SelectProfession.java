package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistance.HeroManager;
import com.herocraftonline.dev.heroes.util.Messaging;
import com.nijiko.coelho.iConomy.iConomy;

public class SelectProfession extends BaseCommand {
    
    public SelectProfession(Heroes plugin) {
        super(plugin);
        name = "Select Profession";
        description = "Selects a new profession";
        usage = "/heroes profession §9<type>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("heroes profession");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            HeroClass profession = plugin.getClassManager().getClass(args[0]);
            if (profession != null) {
                if (profession.isPrimary()) {
                    HeroManager heroManager = plugin.getHeroManager();
                    if (heroManager.getClass(player).equals(plugin.getClassManager().getDefaultClass())) {
                        heroManager.setClass(player, profession);
                    } else {
                        changeClass(player, profession);
                    }
                    Messaging.send(player, "Welcome to the path of the $1!", profession.getName());
                } else {
                    Messaging.send(player, "Sorry, $1 isn't a profession!", profession.getName());
                }
            } else {
                Messaging.send(player, "Sorry, that isn't a profession!");
            }
        }
    }

    public void changeClass(Player player, HeroClass newClass) {
        if (plugin.getConfigManager().getProperties().iConomy == true) {
            if (iConomy.getBank().getAccount(player.getName()).hasOver(plugin.getConfigManager().getProperties().swapcost)) {
                iConomy.getBank().getAccount(player.getName()).add(plugin.getConfigManager().getProperties().swapcost * -1);
            }
            plugin.getHeroManager().setClass(player, newClass);
        } else {
            plugin.getHeroManager().setClass(player, newClass);
        }
    }
}
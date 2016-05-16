# Factions Framework

Want more information on how you can use Factions Framework? See [the wiki](https://github.com/MarkehMe/FactionsFramework/wiki).

Looking to download Factions Framework? It can be found on Spigot, Bukkit, and right [here](https://github.com/MarkehMe/FactionsFramework/releases) on GitHub!

[![Spigot Resources](https://www.spigotmc.org/favicon.ico "Spigot Resources") Spigot Resources](https://www.spigotmc.org/resources/factions-framework.22278/history)

[![BukkitDev](http://dev.bukkit.org/media/site-favicons/0/7/favicon.ico "BukkitDev") BukkitDev](http://dev.bukkit.org/bukkit-plugins/factionsframework/files/)

## What is Factions Framework?

Factions Framework is best described as a middle man. It sits in-between your plugin and the Factions plugin you have installed.

The framework has it's own standard for creating commands, getting factions, using events, and other features required for Factions plugins. This means that you only have to create your plugin to work with Factions Framework. These are automatically routed into Factions, using the correct standard for your installed plugin.

In some cases, Factions Framework is more powerful than using the existing Factions API. We've extended on the features in events and other classes, allowing you to do more things easily.

Factions Framework supports Factions UUID (1.6), FactionsOne (1.8) and most versions of Factions 2.

## Compiling
Using [gradle](http://gradle.org/) you will find the project is quite simple to build.

```bash
# Clone the repo
git clone https://github.com/MarkehMe/FactionsFramework.git && cd FactionsFramework
# Use gradle to compile
gradle jar
# Or, if you don't have gradle installed - there is an internal wrapper you can use
./gradlew jar
```

You can use the [stable tree](https://github.com/MarkehMe/FactionsFramework/tree/stable) to compile the most recent release of Factions Framework.

## Development Builds [![wercker status](https://app.wercker.com/status/92bc69512683cb678863e30e8ba82070/s "wercker status")](https://app.wercker.com/project/bykey/92bc69512683cb678863e30e8ba82070)

[![wercker status](https://app.wercker.com/status/92bc69512683cb678863e30e8ba82070/m "wercker status")](https://app.wercker.com/project/bykey/92bc69512683cb678863e30e8ba82070)

Development builds are not production ready and no support is guaranteed. Things could be broken (and we should assume they are). So please proceed with caution! However, feel free to report bugs.

You can grab development builds at [Wercker](https://app.wercker.com/#RedstoneOre/FactionsFramework). To download development builds, go to the latest build and open the Archive artifacts step.

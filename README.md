# EasyHarvest
## Description
A Bukkit/Spigot plugin for Minecraft 1.21+

## Installation
Put the jarfile into `YourServerFolder/plugins` folder.

## Usage
Holding a ![hoe](https://minecraft.wiki/w/Hoe) in your main hand, right-click a crop block to harvest.

## Config example
```yaml
crops: # Crop whitelist
- WHEAT
- CARROTS
- ETC

tools: # Tool whitelist
- WOODEN_HOE
- ETC

# Features
# Boolean
requireTool: true # Should player hold any of the tools from whitelist?
damageTool: false # Should the held tool get 1 damage if requireTool is true?
randomAge: true # Should crop age be 0-4 or just 0?
giveXP: false # Should harvest give experience?
# Integer
minXP: 1
maxXP: 10
```

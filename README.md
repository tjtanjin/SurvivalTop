
  <h1 align="center">SurvivalTop</h1>
<p align="center">
  <img width=300 src="https://i.imgur.com/nw8EwNU.png" />
</p>

## Table of Contents
* [Introduction](#introduction)
* [Features](#features)
* [Technologies](#technologies)
* [Setup](#setup)
* [Team](#team)
* [Contributing](#contributing)
* [Others](#others)

### Introduction
**SurvivalTop** is a spigot plugin that provides powerful wealth calculation features. If you are looking to get an accurate net worth of your players, then you have come to the right place! On top of being feature-rich, it supports integration with a wide range of other plugins, making itself available for many different users and boasting extremely flexible configurations. The plugin also does majority of its calculation tasks asynchronously, minimizing the performance impact that it can have on a server.

The spigot link to download the plugin can be found **[here](https://www.spigotmc.org/resources/survivaltop.96737/)**. If you require any assistance, please reach out for support on our **[discord](https://discord.gg/X8VSdZvBQY).** Alternatively, you may also open a github issue.

### Features
<p align="center">
  <img src="https://i.imgur.com/672b1LW.gif" />
  <img src="https://i.imgur.com/iDigQoo.gif" />
</p>

Some of the key features provided by the plugin are as shown below:
- Option to include player balance in calculating wealth **(requires  [Vault](https://www.spigotmc.org/resources/vault.34315/))**
- Option to include blocks within land/claims in calculating wealth
- Option to include spawners within land/claims in calculating wealth
- Option to include items in containers (e.g chest, hoppers) within land/claims in calculating wealth
- Option to include items in player inventories in calculating wealth
- Option to assign any values to all block/spawner/container/inventory item types for calculation of wealth
- Option to use PAPI values in calculating wealth
- Option to set calculation mode & heights to optimize performance
- Option to show leaderboard for individual players
- Option to show leaderboard for groups
- Option to set leaderboard signs + heads!
- Option to store leaderboard (YAML/MySQL)
- Support for spawners including but not limited to [**WildStackers**](https://www.spigotmc.org/resources/%E2%9A%A1%EF%B8%8F-wildstacker-%E2%9A%A1%EF%B8%8F-spawners-entities-drops-blocks-%E2%9A%A1%EF%B8%8F-custom-spawn-conditions.60648/),
[**SpawnerMeta**](https://www.spigotmc.org/resources/spawnermeta-fully-customizable-upgradable-modifiable-spawners-1-14-1-20.74188/),
[**MineableSpawners**](https://www.spigotmc.org/resources/mineablespawners-1-8-1-20-silkspawners-alternative.59921/) 
and [**SilkSpawners**](https://www.spigotmc.org/resources/silkspawners-%E2%98%85-ready-to-rock-1-8-1-20-1-supported-%E2%98%85.7811/)
- PlaceholderAPI support **(requires  [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/))**
- Allow players to view their own stats/wealth
- Allow players to view block/spawner/container/inventory item values info
- Fully customizable UI!
- Fully customizable leaderboard update intervals, hover interactions and more!
- Fully customizable messages (with options for your own language files!)

The features above are just a glimpse of what the plugin is capable of. More detailed guides and 
example setups can be found in the **[wiki](https://github.com/tjtanjin/SurvivalTop/wiki)**.

### Technologies
Technologies used by SurvivalTop are as below:
##### Done with:

<p align="center">
  <img height="150" width="150" src="https://brandlogos.net/wp-content/uploads/2013/03/java-eps-vector-logo.png"/>
</p>
<p align="center">
Java
</p>

##### Project Repository
```
https://github.com/tjtanjin/SurvivalTop
```

### Setup
Setting up the SurvivalTop project locally would involve the following steps:
1)  First, `cd` to the directory of where you wish to store the project and fork/clone this repository. An example is provided below:
```
$ cd /home/user/exampleuser/projects/
$ git clone https://github.com/tjtanjin/SurvivalTop.git
```
2) Knowledge of resolving Maven dependencies are assumed here. This is the most tedious part of the setup since there are some plugin integrations that require you to build those JAR files and include them in the `lib` folder of this project.

3) Once you are done with the setup, what's left is for you to make any updates/changes you wish to the code. Once ready, you may build the plugin with the following command:
```
mvn clean install
```
If you are satisfied with your work and would like to contribute to the project, feel free to open a pull request! The forking workflow is preferred in this case so if you have the intention to contribute from the get-go, consider forking this repository before you start!

### Team
* [Tan Jin](https://github.com/tjtanjin)

### Contributing
If you have code to contribute to the project, open a pull request from your fork and describe clearly the changes and what they are intended to do (enhancement, bug fixes etc). Alternatively, you may simply raise bugs or suggestions by opening an issue.

### Others
For any questions regarding the project, please reach out for support via **[discord](https://discord.gg/X8VSdZvBQY).**

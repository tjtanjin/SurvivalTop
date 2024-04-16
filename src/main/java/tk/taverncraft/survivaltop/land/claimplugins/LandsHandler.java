package tk.taverncraft.survivaltop.land.claimplugins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import me.angeschossen.lands.api.framework.blockutil.BlockPosition;
import me.angeschossen.lands.api.land.ChunkCoordinate;
import me.angeschossen.lands.api.land.block.LandBlock;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.blockworks.BoundingBox;
import me.angeschossen.lands.api.land.Container;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.land.LandArea;

import tk.taverncraft.survivaltop.Main;
import tk.taverncraft.survivaltop.land.processor.LandProcessor;
import tk.taverncraft.survivaltop.utils.types.ClaimInfo;

/**
 * Handles land wealth calculated using Lands plugin.
 */
public class LandsHandler implements LandClaimPluginHandler {
    private final Main main;
    private final LandProcessor landProcessor;
    private final LandsIntegration api;

    /**
     * Constructor for LandsHandler.
     *
     * @param main plugin class
     * @param landProcessor helper for land calculations
     */
    public LandsHandler(Main main, LandProcessor landProcessor) {
        this.main = main;
        this.landProcessor = landProcessor;
        this.api = LandsIntegration.of(main);
    }

    /**
     * Gets the claim info for an entity.
     *
     * @param name name of entity to get claim info for
     *
     * @return size 2 array with 1st element = number of claims and 2nd element = number of blocks
     */
    public ClaimInfo getClaimsInfo(String name) {
        long numBlocks = 0;
        Collection<Land> lands = api.getLands();
        double height = main.getOptions().getMaxLandHeight() - main.getOptions().getMinLandHeight();
        for (Land land : lands) {
            Collection<? extends Container> containers = land.getContainers();
            for (Container container : containers) {
                Collection<? extends ChunkCoordinate> chunkCoordinates = container.getChunks();
                numBlocks += chunkCoordinates.size() * 16L * 16L * Double.valueOf(height).longValue();
            }
        }
        return new ClaimInfo(lands.size(), numBlocks);
    }

    /**
     * Processes the worth of a land.
     *
     * @param name name of entity to get land worth for
     * @param id key to identify task
     */
    public void processEntityLand(String name, int id) {
        try {
            Collection<? extends Land> lands = getClaims(name);
            for (Land land : lands) {
                Collection<? extends Container> containers = land.getContainers();
                for (Container container : containers) {
                    Collection<? extends ChunkCoordinate> chunkCoordinates = container.getChunks();
                    World world = container.getWorld().getWorld();
                    for (ChunkCoordinate chunkCoordinate: chunkCoordinates) {
                        Chunk chunk = world.getChunkAt(chunkCoordinate.getX(), chunkCoordinate.getZ());
                        landProcessor.processEntityChunk(id, chunk, world);
                    }
                }
            }

        } catch (NoClassDefFoundError | NullPointerException ignored) {
        }
    }

    /**
     * Gets the claim for entity.
     *
     * @param name name of entity
     */
    private Collection<? extends Land> getClaims(String name) {
        if (this.main.getOptions().groupIsEnabled()) {
            return getClaimsByGroup(name);
        } else {
            return getClaimsByPlayer(name);
        }
    }

    /**
     * Gets claims based on player.
     *
     * @param name name of player to get claims for
     *
     * @return List of claims of player
     */
    private Collection<? extends Land> getClaimsByPlayer(String name) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        CompletableFuture<me.angeschossen.lands.api.player.OfflinePlayer> future =
            api.getOfflineLandPlayer(player.getUniqueId());
        me.angeschossen.lands.api.player.OfflinePlayer offlinePlayer = future.join();
        return offlinePlayer.getLands();
    }

    /**
     * Gets claims based on group.
     *
     * @param name name of group to get claims for
     *
     * @return List of claims of group
     */
    private Collection<? extends Land> getClaimsByGroup(String name) {
        List<OfflinePlayer> players = this.main.getGroupManager().getPlayers(name);
        Collection<Land> collection = new ArrayList<>();
        for (OfflinePlayer player : players) {
            CompletableFuture<me.angeschossen.lands.api.player.OfflinePlayer> future =
            api.getOfflineLandPlayer(Bukkit.getOfflinePlayer(player.getName()).getUniqueId());
            me.angeschossen.lands.api.player.OfflinePlayer offlinePlayer = future.join();
            collection.addAll(offlinePlayer.getLands());
        }

        return collection;
    }
}

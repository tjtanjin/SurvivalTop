package tk.taverncraft.survivaltop.land.claimplugins;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;

import tk.taverncraft.survivaltop.Main;
import tk.taverncraft.survivaltop.land.processor.LandProcessor;
import tk.taverncraft.survivaltop.utils.types.ClaimInfo;

/**
 * Handles land wealth calculated using PlotSquared plugin.
 */
public class PlotSquaredHandler implements LandClaimPluginHandler {
    private final Main main;
    private final LandProcessor landProcessor;

    /**
     * Constructor for PlotSquaredHandler.
     *
     * @param main plugin class
     * @param landProcessor helper for land calculations
     */
    public PlotSquaredHandler(Main main, LandProcessor landProcessor) {
        this.main = main;
        this.landProcessor = landProcessor;
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
        Set<Plot> plots = getClaims(name);
        for (Plot plot: plots) {
            Set<CuboidRegion> regions = plot.getRegions();
            for (CuboidRegion region : regions) {
                double maxY = main.getOptions().getMaxLandHeight();
                double minY = main.getOptions().getMinLandHeight();
                numBlocks += region.getLength() * region.getWidth() * (maxY - minY);
            }
        }
        return new ClaimInfo(plots.size(), numBlocks);
    }

    /**
     * Processes the worth of a land.
     *
     * @param name name of entity to get land worth for
     * @param id key to identify task
     */
    public void processEntityLand(String name, int id) {
        try {
            Set<Plot> plots = getClaims(name);
            for (Plot plot: plots) {
                Set<CuboidRegion> regions = plot.getRegions();
                for (CuboidRegion region : regions) {
                    BlockVector3 pos1 = region.getPos1();
                    BlockVector3 pos2 = region.getPos2();
                    World world = Bukkit.getWorld(plot.getWorldName());
                    Location loc1 = new Location(world, pos1.getX(), pos1.getY(), pos1.getZ());
                    Location loc2 = new Location(world, pos2.getX(), pos2.getY(), pos2.getZ());
                    processEntityClaim(id, loc1, loc2, world);
                }
            }
        } catch (NoClassDefFoundError | NullPointerException ignored) {
        }
    }

    /**
     * Processes the worth of a claim identified between 2 locations.
     *
     * @param id key to identify task
     * @param l1 location 1
     * @param l2 location 2
     * @param world world that the claim is in
     */
    public void processEntityClaim(int id, Location l1, Location l2, World world) {
        double minX = Math.min(l1.getX(), l2.getX());
        double minY = main.getOptions().getMinLandHeight();
        double minZ = Math.min(l1.getZ(), l2.getZ());
        double maxX = Math.max(l1.getX(), l2.getX()) + 1;
        double maxY = main.getOptions().getMaxLandHeight();
        double maxZ = Math.max(l1.getZ(), l2.getZ()) + 1;
        landProcessor.processEntityClaim(id, maxX, minX, maxY, minY, maxZ, minZ, world);
    }

    /**
     * Gets the claim for entity.
     *
     * @param name name of entity
     */
    private Set<Plot> getClaims(String name) {
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
    private Set<Plot> getClaimsByPlayer(String name) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        try {
            PlotPlayer plotPlayer = PlotPlayer.from(player);
            if (plotPlayer == null) {
                return new HashSet<>();
            }
            return plotPlayer.getPlots();
        } catch (IllegalArgumentException e) {
            return new HashSet<>();
        }
    }

    /**
     * Gets claims based on group.
     *
     * @param name name of group to get claims for
     *
     * @return List of claims of group
     */
    private Set<Plot> getClaimsByGroup(String name) {
        List<OfflinePlayer> players = this.main.getGroupManager().getPlayers(name);
        Set<Plot> plots = new HashSet<>();
        for (OfflinePlayer player : players) {
            try {
                PlotPlayer plotPlayer = PlotPlayer.from(player);
                if (plotPlayer != null) {
                    Set<Plot> tempPlots = plotPlayer.getPlots();
                    plots.addAll(tempPlots);
                }
            } catch (IllegalArgumentException ignored) {
            }
        }
        return plots;
    }
}

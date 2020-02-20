public class WorldInfo {
    private final int world;
    private int count;

    public WorldInfo(final int world, final int count) {
        this.world = world;
        this.count = count;
    }

    public int getWorld() {
        return world;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}

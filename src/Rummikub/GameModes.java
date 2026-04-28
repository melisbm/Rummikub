package Rummikub;

public enum GameModes {

    RUMMY{

        private final int minPlayers = 2;
        private final int maxPlayers = 6;

        public int getMinPlayers() {
            return minPlayers;
        }
        public int getMaxPlayers() {
            return maxPlayers;
        }
    },
    CLASSIC{

        private final int minPlayers = 2;
        private final int maxPlayers = 4;

        public int getMinPlayers() {
            return minPlayers;
        }
        public int getMaxPlayers() {
            return maxPlayers;
        }
    },
    GIN{

        private final int minPlayers = 2;
        private final int maxPlayers = 2;

        public int getMinPlayers() {
            return minPlayers;
        }
        public int getMaxPlayers() {
            return maxPlayers;
        }
    },
    ARGENTINIAN{

        private final int minPlayers = 2;
        private final int maxPlayers = 6;

        public int getMinPlayers() {
            return minPlayers;
        }
        public int getMaxPlayers() {
            return maxPlayers;
        }
    };

    public abstract int getMinPlayers();
    public abstract int getMaxPlayers();
}

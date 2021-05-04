package Game;

import Game.Characters.Character;
import Game.Characters.Player;
import Game.Utilitaries.Direction;
import Game.Utilitaries.KeyValuePair;
import Game.Utilitaries.Position;
import Game.Utilitaries.Range;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class Map {
    public static final int MAP_SIZE = 25; //TODO: Maybe change the public final, in case of more maps ?
    public static final KeyValuePair<String, Object> WALL = new KeyValuePair<>("#", null);
    public static final KeyValuePair<String, Object> GROUND = new KeyValuePair<>(" ", null);

    //TODO: Change to one-dimensional array, faster but needs reformating on the map every MAP_SIZE length.
    private final KeyValuePair<String, Object>[][] buffer;

    //region Constructors

    /**
     * Default ctor.
     * Creates a new Client.Map from MAP_SIZE.
     */
    public Map() {
        this.buffer = new KeyValuePair[MAP_SIZE][];

        for (int line = 0; line < buffer.length; line++) {
            var array = new KeyValuePair[MAP_SIZE];

            if (line == 0 || line == buffer.length - 1) {
                Arrays.fill(array, WALL);
            } else {
                for (int column = 0; column < array.length; column++) {

                    if (column == 0 || column == array.length - 1) {
                        array[column] = WALL;
                    } else {
                        array[column] = GROUND;
                    }
                }
            }
            buffer[line] = array;
        }
    }

    /**
     * Copy ctor.
     * Copy and create a new Client.Map from an existing Client.Map.
     *
     * @param map Client.Map to copy.
     */
    public Map(Map map) {
        this.buffer = new KeyValuePair[map.getBuffer().length][];
        System.arraycopy(map.getBuffer(), 0, buffer, 0, buffer.length);
    }

    /**
     * Copy ctor.
     * Copy and create a new Client.Map from a string array.
     *
     * @param array String array to copy.
     */
    public Map(KeyValuePair<String, Object>[][] array) {
        this.buffer = new KeyValuePair[array.length][];
        System.arraycopy(array, 0, this.buffer, 0, buffer.length);
    }

    //endregion

    //region Accessors

    /**
     * @return Buffer of the current map.
     */
    public KeyValuePair<String, Object>[][] getBuffer() {
        return buffer;
    }

    public KeyValuePair<String, Object> getCase(int value) {
        return getBuffer()[value/MAP_SIZE][value%MAP_SIZE];
    }

    //endregion

    //region Methods

    /**
     * @param position The position to look at if empty.
     * @return True if (x,y) equals ground, true if out of map, false if not equals ground.
     */
    public boolean isEmpty(Position position) {
        if (position.getY() > MAP_SIZE - 1 && position.getY() < 1 || position.getX() > MAP_SIZE - 1
                && position.getX() < 1) {
            return false;
        }
        return this.buffer[position.getY()][position.getX()].getKey().equals(GROUND.getKey());
    }

    /**
     * @param direction The direction in which the character will go.
     * @param character The instance of the character.
     * @return True if the next position will be ground, true if out of map, false if ground.
     */
    public boolean isEmpty(Direction direction, Character character) {
        if (character.getPosition().getY() > MAP_SIZE - 1 && character.getPosition().getY() < 1
                || character.getPosition().getX() > MAP_SIZE - 1 && character.getPosition().getX() < 1) {
            return false;
        }

        Position position = new Position();
        switch (direction) {
            case Up:
                position.moveUp();
                break;

            case Down:
                position.moveDown();
                break;

            case Left:
                position.moveLeft();
                break;

            case Right:
                position.moveRight();
                break;
        }
        return this.buffer[character.getPosition().getY() + position.getY()][character.getPosition().getX() + position.getX()]
                .getKey().equals(GROUND.getKey());
    }

    /**
     * Change the position of a player with a direction
     *
     * @param player    The instance of the character.
     * @param direction The direction: Up, Down, Left, Right
     */
    public void move(Character player, Direction direction) {
        this.put(GROUND, player.getPosition());
        player.move(direction);
        this.put(new KeyValuePair<>(java.lang.Character.toString(player.getReference()), player), player.getPosition());
    }

    /**
     * Spawn a character at specific location.
     *
     * @param player The instance of the character to spawn.
     * @param position The position on which the character will spawn.
     */
    public void spawn(Character player, Position position) {
        player.setPosition(position);
        this.put(new KeyValuePair<>(java.lang.Character.toString(player.getReference()), player), player.getPosition());
    }

    /**
     * Gives a KeyValuePair to a position in the buffer.
     *
     * @param keyValuePair    The KeyValuePair to give.
     * @param position The position where it will be put.
     */
    public void put(KeyValuePair<String, Object> keyValuePair, Position position) {
        this.buffer[position.getY()][position.getX()] = keyValuePair;
    }

    /**
     * @return Returns an empty position x,y from the buffer.
     */
    public Position findEmptyPosition() {
        Position position = new Position();
        while (!isEmpty(position)) {
            position.randomPos(MAP_SIZE);
        }
        return position;
    }

    /**
     * Put one or more walls in random directions depending on the amount.
     *
     * @param amount Amount of wall to put AFTER the first one.
     */
    public void randomWall(int amount) {
        Position position = findEmptyPosition();
        this.put(WALL, position);
        for (int i = 0; i < amount; i++) {
            Direction direction = Direction.randomDirection();
            switch (direction) {
                case Up:
                    position.moveUp();
                    break;
                case Down:
                    position.moveDown();
                    break;
                case Left:
                    position.moveLeft();
                    break;
                case Right:
                    position.moveRight();
                    break;
            }

            if (isEmpty(position)) {
                this.put(WALL, position);
            }
        }
    }


    public KeyValuePair<String, Object>[][] randerDistance(Player player) {
        KeyValuePair<String, Object>[][] mapView = new KeyValuePair[player.getStatistics().getRange().getView()*2+1][player.getStatistics().getRange().getView()*2+1];



        int y = -player.getStatistics().getRange().getView();
        for(int line=0; line <= mapView.length-1 ; line++) {

            if( (player.getPosition().getY() + y) >= 0 && (player.getPosition().getY() + y) <= buffer.length-1) {
                int x = -player.getStatistics().getRange().getView();
                for (int column = 0; column <= mapView.length - 1; column++) {


                    if( (player.getPosition().getX() + x) >= 0 && (player.getPosition().getX() + x) <= buffer.length-1) {
                        mapView[line][column] = new KeyValuePair<>(buffer[player.getPosition().getY()+y][player.getPosition().getX()+x].getKey(), buffer[player.getPosition().getY() + y][player.getPosition().getX() + x].getValue());
                    }
                    else{
                        mapView[line][column] = new KeyValuePair<>("X", null);
                    }
                    x++;
                }
            }
            else{
                for (int column = 0; column <= mapView.length-1; column++){
                    mapView[line][column] = new KeyValuePair<>("X",null);
                }
            }
            y++;
        }
        return mapView;
    }



    public String readMap(KeyValuePair<String,Object>[][] buffer){

        StringBuilder s = new StringBuilder();
        for (KeyValuePair<String, Object>[] line : buffer) {
            s.append("|");

            for (int i = 0; i < line.length; i++) {
                KeyValuePair<String, Object> column = line[i];
                s.append(column.getKey());

                if (i == line.length - 1) {
                    s.append("|");
                    continue;
                }

                s.append("  ");
            }
            s.append("\n");
        }
        return s.toString();

    }

    //endregion

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (KeyValuePair<String, Object>[] line : buffer) {
            s.append("[");

            for (int i = 0; i < line.length; i++) {
                KeyValuePair<String, Object> column = line[i];
                s.append(column.getKey());

                if (i == line.length - 1) {
                    s.append("]");
                    continue;
                }

                s.append("  ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}

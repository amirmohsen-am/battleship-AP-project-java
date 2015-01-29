package battleship;

import battleship.equipment.AntiAircraft;
import battleship.equipment.Equipment;
import battleship.equipment.Mine;
import battleship.equipment.Ship;
import battleship.exception.GameOverException;
import battleship.exception.NoMoreInputException;
import battleship.frame.PlayingFrame;
import battleship.graphic.Graphic;
import battleship.graphic.GraphicObject;
import battleship.graphic.image.GameImages;
import battleship.position.Position;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/** Controls the relation between GameEngine and I/O
 *
 * @author Ahanchi
 */
public class GameController {

    private Graphic[] graphic;
    private GameEngine engine;
    private Log log;
    private ConsoleInput consoleInput;

    boolean gameHasEnded = false;
    PlayingFrame playingFrame;

    /** Initializes the engine, log and consoleInput and also sets controller and owner of all the equipments of the players
     *
     * @param engine the GameEngine
     * @param log the Log
     * @param consoleInput the consoleInput
     */
    public void init(final GameEngine engine, Log log, ConsoleInput consoleInput, final PlayingFrame playingFrame, boolean online, int playerNumber) {
        this.engine = engine;
        this.log = log;
        this.consoleInput = consoleInput;
        this.playingFrame = playingFrame;
        this.graphic = new Graphic[2];
        graphic[0] = engine.getPlayers()[0].getGraphic();
        graphic[1] = engine.getPlayers()[1].getGraphic();

        for (Player player : engine.getPlayers()) {
            for (Equipment equipment : player.getMap().getEquipments()) {
                equipment.setController(this);
                equipment.setOwner(player);
            }
            player.getMap().setOwner(player);
            player.getMap().setController(this);
        };
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
                playingFrame.init(GameController.this, engine, online, playerNumber);
//            }
//        });

    }

    public ConsoleInput getConsoleInput() {
        return consoleInput;
    }

    public Graphic[] getGraphic() {
        return graphic;
    }

    /** Starts and continues the game up to an ending point*/
    public void start() throws IOException {
        gameHasEnded = false;
        Thread paintThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!gameHasEnded) {
                    playingFrame.repaint();
                    try {
                        Thread.sleep(1000 / Graphic.FPS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        paintThread.start();
        Thread goThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!gameHasEnded) {
                    try {
                        Thread.sleep(GameEngine.GO_MS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        engine.update();
                    } catch (GameOverException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                        gameHasEnded = true;
                    }
                }
                System.exit(0);

            }
        });
        goThread.start();

        while (!gameHasEnded) {
            try {
                consoleInput.next();
            } catch (GameOverException e) {
                log.println(e.getMessage());
                gameHasEnded = true;
            } catch (NoMoreInputException e) {
                gameHasEnded = true;
            }
        }
    }


    public GameEngine getEngine() {
        return engine;
    }

    /** Runs an attack command
     *
     * @param targetPosition the position which is attacked
     * @param attackingPlayer the player who has attacked
     * @throws Exception if the equipment in that position is already attacked
     */
    public void attack(Position targetPosition, Player attackingPlayer) throws GameOverException {
        engine.getOpponent(attackingPlayer).getMap().setVisible(targetPosition.x, targetPosition.y, true);
        engine.attack(targetPosition, attackingPlayer);

    }

    /** Runs a radar command to identify the equipments around a position
     *
     * @param targetPosition the position to perform the radar command on
     * @param attackingPlayer the player who has commanded the radar command
     */
    public void radar(Position targetPosition, Player attackingPlayer) {
        engine.radar(targetPosition, attackingPlayer);
    }

    /** Calls an aircraft attack
     *
     * @param row the row on whicj the aircraft will attack
     * @param attackingPlayer the player who has launched the aircraft
     * @throws Exception if an attacked eqipment has already been exploded
     */
    public void aircraft(int row, Player attackingPlayer) throws GameOverException {
        engine.aircraft(row, attackingPlayer);
    }

    /** Reports an explosion of cell of a ship
     *
     * @param targetPosition the position of a ship which has been exploded
     * @param attackedPlayer the player who's cell has exploded
     */
    public void reportShipCellExplode(Position targetPosition, Player attackedPlayer) {
        log.println("team " + engine.getOpponent(attackedPlayer).getName() + " explode " +  targetPosition.getString());
        attackedPlayer.getGraphic().addGraphicObject(new GraphicObject(
                targetPosition, GameImages.ExplodeAnimation, GameImages.ExplodeAnimationSpeed, false) );
        attackedPlayer.getGraphic().addGraphicObject(new GraphicObject(
                targetPosition, GameImages.Fire, GameImages.FireSpeed, true, 6) );
    }

    /** Reports complete explosion of a ship
     *
     * @param ship the ship that has been completely destroyed
     * @throws battleship.exception.GameOverException if the explosion if the ship ends the game
     */
    public void reportShipDestroyed(Ship ship) throws GameOverException {
//        log.println("ship " + ship.getName() + " destroyed");

        if (ship.getOwner().getMap().isAllShipDestroyed())
            throw new GameOverException("team " + engine.getOpponent(ship.getOwner()).getName() + " wins");
    }

    /** Reports explosion of a mine
     *
     * @param mine the mine that has been exploded
     * @throws Exception if the mines causes explosion of an already exploded cell
     */
    public void reportMineExplode(Mine mine) throws GameOverException {
        log.println("team " + engine.getOpponent(mine.getOwner()).getName() + " mine trap " + mine.getPosition().getString());
        this.attack(mine.getPosition(), mine.getOwner());
        mine.getOwner().getGraphic().addGraphicObject(new GraphicObject(
                mine.getPosition(), GameImages.ExplodeAnimation, GameImages.ExplodeAnimationSpeed, false));
        mine.getOwner().getGraphic().addGraphicObject(new GraphicObject(
                mine.getPosition(), GameImages.Fire, GameImages.FireSpeed, true, 6) );

        //engine.addEvent(0, "team " + mine.getOwner().getName() + " attack " + mine.getPosition().getString());
    }

    /** Reports defence of an antiAircraft against an aircraft
     *
     * @param antiAircraft the antiAircraft that has defended against the aircraft
     */
    public void reportAircraftHit(AntiAircraft antiAircraft) {
        log.println("aircraft unsuccessful");


    }

    /** Reports explosion of an antiAircraft that has benn hit directly
     *
     * @param antiAircraft the antiAircraft that has been exploded
     */
    public void reportAntiAircraftHitDirectly(AntiAircraft antiAircraft) {
        log.println("team " + antiAircraft.getOwner().getName() + " anti aircraft row " + antiAircraft.getRow() + " exploded");

    }

    /** Reports the identified positions of equipments after running a radar command
     *
     * @param positions the positions that has been identified by radar command
     * @param owner the player whose cells have been identified
     */
    public void reportRadar(ArrayList<Position> positions, Player owner, Position middlePosition) {
        for (Position position : positions) {
            log.println("team " + engine.getOpponent(owner).getName() + " detected " + position.getString());
        }
        for (int i = 0; i < Map.getHeight(); i++)
            for (int j = 0; j < Map.getWidth(); j++) {
                Position mapPosition = new Position(j, i);
                if (mapPosition.getMaxDistance(middlePosition) <= GameEngine.RADAR_RADIUS)
                    owner.getMap().setVisible(mapPosition.x, mapPosition.y, true);
            }

//        owner.getGraphic().addGraphicObject(new GraphicObject(
//                middlePosition, GameImages.RadarAnimation, GameImages.RadarSpeed, false, 5) );
    }

}

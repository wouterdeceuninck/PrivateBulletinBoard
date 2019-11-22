package bulletinBoard.collection;

import bulletinBoard.exceptions.NoSuchCellException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import security.utils.HashFunction;

public class ExceptionHandlingBulletinBoard extends BulletinBoard {
    private Logger logger = LoggerFactory.getLogger(ExceptionHandlingBulletinBoard.class);

    public ExceptionHandlingBulletinBoard(int amount, HashFunction hashFunction) {
        super(amount, hashFunction);
    }

    @Override
    public void add(int cell, String tag, String value) {
        logger.info("Value added to cell {} with the hashedTag {}", cell, tag);
        try {
            super.add(cell, tag, value);
        } catch (NoSuchCellException e) {
            logger.error("A request was send for a cell that does not exist!");
        }
    }

    @Override
    public String get(int cell, String tag) {
        logger.info("Value requested from cell {} with the tag {}", cell, tag);
        try {
            return super.get(cell, tag);
        } catch (NoSuchCellException e) {
            logger.error("A request was send for a cell that does not exist!");
            return "";
        }
    }

}

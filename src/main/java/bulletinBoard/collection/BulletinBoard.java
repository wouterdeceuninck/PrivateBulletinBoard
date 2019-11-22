package bulletinBoard.collection;

import bulletinBoard.cell.Cell;
import bulletinBoard.exceptions.NoSuchCellException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import security.utils.HashFunction;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

public class BulletinBoard implements BulletinBoardInterface {
    private Map<Integer, Cell> cellCollection;

    public BulletinBoard(int amount, HashFunction hashFunction) {
        cellCollection = range(0, amount)
                .mapToObj(iter -> createCellEntry(iter, hashFunction))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private SimpleEntry<Integer, Cell> createCellEntry(int cellNumber, HashFunction hashFunction) {
        return new SimpleEntry<>(cellNumber, new Cell(hashFunction));
    }

    @Override
    public void add(int cell, String tag, String value) {
        Cell selectedCell = getSelectedCell(cell);
        selectedCell.putMessage(tag, value);
    }

    @Override
    public String get(int cell, String tag) {
        Cell selectedCell = getSelectedCell(cell);
        return selectedCell.getMessage(tag);
    }

    private Cell getSelectedCell(int cell) {
        Cell selectedCell = cellCollection.get(cell);
        if (selectedCell == null) {
            throw new NoSuchCellException("The selected cell does not exist!");
        }
        return selectedCell;
    }
}

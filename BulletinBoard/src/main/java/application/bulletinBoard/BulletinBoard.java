package application.bulletinBoard;

import application.bulletinBoard.cell.Cell;
import application.exceptions.NoSuchCellException;
import presentation.security.HashFunction;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

public class BulletinBoard {
    private final Map<Integer, Cell> cellCollection;

    BulletinBoard(int amount, HashFunction hashFunction) {
        cellCollection = range(0, amount)
                .mapToObj(iter -> createCellEntry(iter, hashFunction))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private SimpleEntry<Integer, Cell> createCellEntry(int cellNumber, HashFunction hashFunction) {
        return new SimpleEntry<>(cellNumber, new Cell(hashFunction));
    }

    public void add(int cell, String tag, String value) {
        Cell selectedCell = getSelectedCell(cell);
        selectedCell.putMessage(tag, value);
    }

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

package application.bulletinBoard;

import application.bulletinBoard.cell.Cell;
import application.exceptions.NoSuchCellException;
import shared.security.HashFunction;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

public class BulletinBoard {
    private Map<Integer, Cell> cellCollection;
    private final HashFunction hashFunction;

    BulletinBoard(int amount, HashFunction hashFunction) {
        cellCollection = range(0, amount)
                .mapToObj(this::createCellEntry)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        this.hashFunction = hashFunction;
    }

    public BulletinBoard(int start, int end, HashFunction hashFunction) {
        this.hashFunction = hashFunction;
        cellCollection = range(start, end + 1)
                .mapToObj(this::createCellEntry)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private SimpleEntry<Integer, Cell> createCellEntry(int cellNumber) {
        return new SimpleEntry<>(cellNumber, new Cell());
    }

    public void add(int cell, String tag, String value) {
        Cell selectedCell = getSelectedCell(cell);
        selectedCell.putMessage(tag, value);
    }

    public String get(int cell, String tag) {
        Cell selectedCell = getSelectedCell(cell);
        return selectedCell.getMessage(hashFunction.hashString(tag));
    }

    private Cell getSelectedCell(int cell) {
        Cell selectedCell = cellCollection.get(cell);
        if (selectedCell == null) {
            throw new NoSuchCellException("The selected cell does not exist!");
        }
        return selectedCell;
    }
}

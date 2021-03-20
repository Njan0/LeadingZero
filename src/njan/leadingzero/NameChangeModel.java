/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njan.leadingzero;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Jan
 */
public class NameChangeModel extends AbstractTableModel {
    private final List<Row> list = new ArrayList<>();
    private final String[] columnNames = {"Old", "New"};
    private static final int SRC_INDEX = 0;
    private static final int DST_INDEX = 1;
    
    public enum State {
        enabled,
        collision,
        noChange
    }

    public NameChangeModel() {
    }
    
    public NameChangeModel(String[][] data, State[] states) {
        for (int i = 0; i < data.length; ++i) {
            var src = data[i][SRC_INDEX];
            var dst = data[i][DST_INDEX];
            list.add(new Row(src, dst, states[i]));
        }
    }

    public State getState(int row) {
        return list.get(row).state;
    }

    public void setState(int row, State state) {
        list.get(row).state = state;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch(col) {
            case SRC_INDEX -> {
                return list.get(row).src;
            }
            case DST_INDEX -> {
                return list.get(row).dst;
            }
        }
        
        throw new IndexOutOfBoundsException();
    }

    @Override
    public void setValueAt(Object aValue, int row, int col) {
        switch(col) {
            case SRC_INDEX -> {
                list.get(row).src = (String) aValue;
            }
            case DST_INDEX -> {
                list.get(row).dst = (String) aValue;
            }
            default -> {
                throw new IndexOutOfBoundsException();
            }
        }
        fireTableCellUpdated(row, col);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    private static class Row {
        String src;
        String dst;
        State state;

        public Row(String src, String dst, State state) {
            this.src = src;
            this.dst = dst;
            this.state = state;
        }
    }
    
    public static class CustomRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            NameChangeModel model = (NameChangeModel) table.getModel();
            if (!isSelected){
                switch(model.getState(row)) {
                    case enabled -> {
                        c.setForeground(Color.BLACK);
                        c.setBackground(Color.WHITE);
                    }
                    case collision -> {
                        c.setForeground(Color.BLACK);
                        c.setBackground(Color.RED);
                    }
                    case noChange -> {
                        c.setForeground(Color.GRAY);
                        c.setBackground(Color.WHITE);
                    }
                }
            }
            return c;
        }
    }
}

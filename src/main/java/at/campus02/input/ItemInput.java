package at.campus02.input;

import at.campus02.models.Item;
import at.campus02.storage.Database;

import java.io.EOFException;

public class ItemInput {
    private final InputHelper inputHelper;

    public ItemInput() {
        this.inputHelper = new InputHelper();
    }

    public void viewItem() throws EOFException {
        System.out.println("--- View item ---");
        Integer number = inputHelper.getItemId(InputHelper.ID_MUST_EXIST);
        System.out.println(Database.items.get(number));
    }

    public void addItem() throws EOFException {
        System.out.println("--- Add item ---");
        Item newItem = new Item();

        newItem.id = inputHelper.getItemId(InputHelper.ID_MUST_NOT_EXIST);
        newItem.name = inputHelper.getString("Name");
        newItem.description = inputHelper.getString("Description");
        newItem.price = inputHelper.getDecimal("Price");

        Database.items.put(newItem.id, newItem);
        System.out.println("Item added.");
    }

    public void removeItem() throws EOFException {
        System.out.println("--- Remove item ---");
        Integer number = inputHelper.getItemId(InputHelper.ID_MUST_EXIST);
        Database.items.remove(number);
        System.out.println("Item removed.");
    }
}

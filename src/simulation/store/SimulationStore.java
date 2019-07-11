package simulation.store;

import java.io.*;
import java.util.ArrayList;

public class SimulationStore {

    public SimulationStore() {
    }

    public ArrayList<?>  readAllItem(String nameFile) {
        ArrayList<?> list = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(nameFile));
            list = (ArrayList<?>) objectInputStream.readObject();
            objectInputStream.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void writeAllItem(String nameFile, ArrayList<?> list){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(nameFile));
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

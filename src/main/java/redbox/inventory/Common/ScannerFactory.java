package redbox.inventory.Common;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/*
Singleton Scanner Factory
Hosanna Pyles
hpp220001
 */

public abstract class ScannerFactory {
    private static Scanner _scannerObject; 

    public static Scanner GetScannerInstance(File file)
    {
        try {
            if(_scannerObject == null)
            {
                _scannerObject = new Scanner(file); 
            }
        } catch(FileNotFoundException e) {
            System.out.println("Error loading file: ");
            e.printStackTrace();
        }

        return _scannerObject;
    }

    public static void CloseScannerInstance()
    {
        _scannerObject.close();
        _scannerObject = null; 
    }
}

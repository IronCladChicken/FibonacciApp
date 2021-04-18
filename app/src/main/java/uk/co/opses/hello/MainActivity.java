package uk.co.opses.hello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static int REQUEST_CODE_SELECT_DIRECTORY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckFilePath();

    }

    public void buttonRun(View view) {

        if (CheckFilePath()) { //Probably a needless extra check, but just in case there's been a change.

            //Get data from the text field
            EditText editText = findViewById(R.id.editTextNumberDecimal);
            String userValue = editText.getText().toString();

            //Check that it contains a value (the xml restricts input to an int of 2 characters
            if (userValue.length() > 0) {

                //Run the Fibonacci algorithm & store the returned results in a list.
                List<Integer> results = FibonacciAlgorithm.Run(Integer.parseInt(userValue));

                //If there are results
                if (results.size() > 0) {

                    //Format results (add '\n' after each entry)
                    String outputMe = formatResults(results);

                    //save results to the selected directory
                    saveToStorage(outputMe);

                }

            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {
                //If the user selected path looks good - save to preferences
                writeToSharedPreferences(resultData.getData().toString());
            }

            //Ensure selected path is correct (e.g. not a file)
            CheckFilePath();

        }

    }

    //---

    //Manage file path....
    public boolean CheckFilePath() {

        boolean filePathValid = false;

        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String path = sharedPref.getString(getString(R.string.fileSaveDirectoryPathKey), getString(R.string.noSharedPrefKeyFound));

        //If no path is found in shared prefs - request the user select one
        if (path.equals(getString(R.string.noSharedPrefKeyFound))) {
            //Log.d("sharedPref", "NO");
            Toast.makeText(MainActivity.this, getString(R.string.noSaveDirectoryFound), Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, getString(R.string.pleaseSelectASaveDirectory), Toast.LENGTH_LONG).show();
            openDirectory();

        } else {

            //If a path is found - Confirm it is valid and points at a directory...
            DocumentFile documentFile = DocumentFile.fromTreeUri(this, Uri.parse(path));

            //If it doesn't (or the user doesn't have write permissions) - request the user select a replacement directory
            if (!documentFile.isDirectory() || !documentFile.canWrite()) {

                Toast.makeText(MainActivity.this, getString(R.string.saveDirectoryInvalid), Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, getString(R.string.pleaseSelectASaveDirectory), Toast.LENGTH_LONG).show();
                openDirectory();

            } else

                filePathValid = true;

        }

        return filePathValid;

    }

    public void openDirectory() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, REQUEST_CODE_SELECT_DIRECTORY);
    }

    public void writeToSharedPreferences(String pathToSave) {
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.fileSaveDirectoryPathKey), pathToSave);
        editor.apply();
    }

    //---

    String formatResults(List<Integer> results){
        String outputMe = "";
        for (int i = 0; i < results.size(); i++)
            outputMe += results.get(i) + "\n";
        return outputMe.substring(0, outputMe.length() - 1); //get rid of that last \n
    }

    //Save file to storage
    void saveToStorage(String fileContents) {
        //get save path
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String pickerInitialUri = sharedPref.getString(getString(R.string.fileSaveDirectoryPathKey), getString(R.string.noSharedPrefKeyFound));

        String filename = getString(R.string.fileName);

        DocumentFile docFile;
        DocumentFile resultFile;

        //---

        //Check to see if a copy of the file already exists - if it does, delete it first
        Uri fileUri = Uri.withAppendedPath(Uri.parse(pickerInitialUri), filename);
        docFile = DocumentFile.fromTreeUri(MainActivity.this, fileUri);

        if (docFile != null) {

            resultFile = docFile.findFile(filename);

            if (resultFile != null) {
                resultFile.delete();
            }

        }

        //---

        //Create a new file
        Uri uri = Uri.parse(pickerInitialUri);
        docFile = DocumentFile.fromTreeUri(MainActivity.this, uri);
        resultFile = docFile.createFile("text/plain", filename);

        if (resultFile == null) {
            resultFile = docFile.findFile(filename);
        }

        //---

        //Save data to the new file
        try {

            OutputStream out = getContentResolver().openOutputStream(resultFile.getUri());
            out.write(fileContents.getBytes());
            out.flush();
            out.close();

            Toast.makeText(MainActivity.this, getString(R.string.FibonacciResultsGeneratedAndSavedSuccessfully), Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException ex) {
            Toast.makeText(MainActivity.this, getString(R.string.ErrorSavingFileUnableToCreateFile), Toast.LENGTH_LONG).show();
            ex.printStackTrace();

        } catch (IOException e) {
            Toast.makeText(MainActivity.this, getString(R.string.ErrorSavingFileUnableToWriteDataToFile), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    //---

}
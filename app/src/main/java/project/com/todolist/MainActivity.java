package project.com.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

    //Atributes
    private EditText todoText;
    private Button addBtn;
    private ListView todoList;
    private SQLiteDatabase dB;
    private String dBName = "appDB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set layout components
        todoText = findViewById(R.id.todoText);
        addBtn = findViewById(R.id.addBtn);
        todoList = findViewById(R.id.todoList);

        //Create DataBase if not exists
        dB = openOrCreateDatabase(dBName, MODE_PRIVATE, null);

        //Create Table if not exists
        dB.execSQL("CREATE TABLE IF NOT EXISTS todolist(id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR)");

    }
}

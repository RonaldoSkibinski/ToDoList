package project.com.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

        try {

            //Set layout components
            todoText = findViewById(R.id.todoText);
            addBtn = findViewById(R.id.addBtn);
            todoList = findViewById(R.id.todoList);

            //Create DataBase if not exists
            dB = openOrCreateDatabase(dBName, MODE_PRIVATE, null);

            //Create Table if not exists
            dB.execSQL("CREATE TABLE IF NOT EXISTS todolist(id INTEGER PRIMARY KEY AUTOINCREMENT, todo VARCHAR)");

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveTodo(todoText.getText().toString());
                }
            });

        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    //Save
    private void saveTodo(String todo) {

        if(!todo.equals("")) {
            try {
                dB.execSQL("INSERT INTO todolist (todo) values ('" + todo + "')");
                Toast toast = Toast.makeText(this, "Tarefa Salva", Toast.LENGTH_LONG);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }else {
            Toast toast = Toast.makeText(this, "Digite Uma Tarefa", Toast.LENGTH_LONG);
        }

    }
}

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            //Set layout components
            todoText = findViewById(R.id.todoText);
            addBtn = findViewById(R.id.addBtn);
            todoList = findViewById(R.id.todoList);

            createDB("appDb");

            //Create Table if not exists
            sql("CREATE TABLE IF NOT EXISTS todolist(id INTEGER PRIMARY KEY AUTOINCREMENT, todo VARCHAR)");

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

    //Create db function
    private void createDB(String dBName) {
        //Create DataBase if not exists
        dB = openOrCreateDatabase(dBName, MODE_PRIVATE, null);
    }

    //Query function
    private void sql(String sql) {
        dB.execSQL(sql);
    }

    //SaveTodo
    private void saveTodo(String todo) {

        Toast toast = null;
        if(!todo.equals("")) {
            try {
                sql("INSERT INTO todolist (todo) values ('" + todo + "')");
                toast = Toast.makeText(this, "Tarefa Salva", Toast.LENGTH_LONG);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }else {
            toast = Toast.makeText(this, "Digite Uma Tarefa", Toast.LENGTH_LONG);
        }
        toast.show();

    }

}

package project.com.todolist;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    //Atributes
    private EditText todoText;
    private Button addBtn;
    private ListView todoList;
    private SQLiteDatabase dB;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;
    private String getValues = "SELECT * FROM todolist ORDER BY id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast toast = Toast.makeText(this, "Clique e segure um item para concluir a tarefa!", Toast.LENGTH_LONG);
        toast.show();

        try {

            //Set layout components
            todoText = findViewById(R.id.todoText);
            addBtn = findViewById(R.id.addBtn);

            //toDOList
            todoList = findViewById(R.id.todoList);

            createDB("appDb");

            //Create Table if not exists
            sql("CREATE TABLE IF NOT EXISTS todolist(id INTEGER PRIMARY KEY AUTOINCREMENT, todo VARCHAR)", false);

            //Get the table values and insert the values in the list
            sql(getValues, true);

            //addBtn click listener
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveTodo(todoText.getText().toString());
                    todoText.setText("");
                }
            });

            //Long click item remover listview
            todoList.setLongClickable(true);
            todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    removeItem(ids.get(i));
                    return true;
                }
            });

        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    //Create db function
    private void createDB(String dBName) {
        //Create DataBase if not exists
        try{
            dB = openOrCreateDatabase(dBName, MODE_PRIVATE, null);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Query function
    private void sql(String sql, boolean get) {
        if(!get) {
            try{
                dB.execSQL(sql);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }else {
            //Create a cursor for get the values from table
            Cursor cursor = dB.rawQuery(sql, null);

            //Create index to cursor
            int indiceColumnId = cursor.getColumnIndex("id");
            int indiceColumnTodo = cursor.getColumnIndex("todo");

            itens = new ArrayList<>();
            ids = new ArrayList<>();

            //List the values
            if(cursor.moveToFirst()) {
                while(cursor.moveToNext()) {
                    ids.add(Integer.parseInt(cursor.getString(indiceColumnId)));
                    itens.add(cursor.getString(indiceColumnTodo));
                }
            }

            //Instance the adapter
            adapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    itens);

            todoList.setAdapter(adapter);
        }
    }

    //SaveTodo
    private void saveTodo(String todo) {

        Toast toast = null;
        if(!todo.equals("")) {
            try {
                sql("INSERT INTO todolist (todo) values ('" + todo + "')", false);
                toast = Toast.makeText(this, "Tarefa Salva", Toast.LENGTH_LONG);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }else {
            toast = Toast.makeText(this, "Digite Uma Tarefa", Toast.LENGTH_LONG);
        }
        toast.show();
        sql(getValues, true);

    }

    //Remove item
    private void removeItem(Integer id) {
        try{
            sql("DELETE FROM todoList WHERE id = "+ id, false);
            sql(getValues, true);
            Toast toast = Toast.makeText(this, "Tarefa Completa!", Toast.LENGTH_LONG);
            toast.show();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}

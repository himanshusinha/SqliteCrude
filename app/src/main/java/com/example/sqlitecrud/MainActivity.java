package com.example.sqlitecrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button button,button2,button_delete,button_update;
    EditText editname,editphone,edit_id,edit_update_id,edit_update_name;
    DataBaseHelper myDB;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB=new DataBaseHelper(this);
        editname=(EditText)findViewById(R.id.name_edit);
        editphone=(EditText)findViewById(R.id.phone_edit);
        edit_id=(EditText)findViewById(R.id.edit_delete);
        edit_update_id=(EditText)findViewById(R.id.update_id);
        edit_update_name=(EditText)findViewById(R.id.update_name_edit);

        button=(Button)findViewById(R.id.btn_save);
        button2=(Button)findViewById(R.id.btn_display);
        button_delete=(Button)findViewById(R.id.delete_btn);
        button_update=(Button)findViewById(R.id.update_btn);

        viewAll();
        Adddata();
        DeleteData();
        UpdateData();
    }

    private void UpdateData() {
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id=edit_update_id.getText().toString();
                boolean idupdate=myDB.updateData(id,edit_update_name.getText().toString());

                if(edit_update_id.getText().toString().isEmpty())
                {
                    edit_update_id.setError("Please Enter id");
                    edit_update_id.requestFocus();
                }
                else
                {
                    if(idupdate)
                    {
                        Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        edit_update_id.setText("");
                        edit_update_name.setText("");
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Not Updated", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void DeleteData() {
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=edit_id.getText().toString();

                Integer delrow=myDB.deleteData(id);

                if(delrow>0)
                {
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    edit_id.setText("");
                }
                else
                {
                    if(edit_id.getText().toString().isEmpty())
                    {
                        edit_id.setError("Please Enter Id");
                        edit_id.requestFocus();
                    }
                }
            }
        });
    }

    private void viewAll() {
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor res=myDB.getAllData();
                if(res.getCount()==0)
                {
                    showMessage("Alert","Nothing Found");
                }
                StringBuffer buffer=new StringBuffer();
                while (res.moveToNext())
                {
                    buffer.append("Id:"+res.getString(0)+"\n");
                    buffer.append("Name:"+res.getString(1)+"\n");
                    buffer.append("Phone_no:"+res.getString(2)+"\n\n");
                }
                showMessage("Data",buffer.toString());
            }
        });
    }

    private void Adddata() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=editname.getText().toString();
                String phone=editphone.getText().toString();

                if(validation())
                {
                    boolean isinserted=myDB.insertData(name,phone);
                    if(isinserted)
                    {
                        Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                        editname.setText("");
                        editphone.setText("");
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Data not Inserted",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public boolean validation()
    {
        if(editname.getText().toString().isEmpty())
        {
            editname.setError("Please Enter Name");
            editname.requestFocus();
            return false;
        }
        if(editphone.getText().toString().isEmpty())
        {
            editphone.setError("Please Enter Phone no");
            editphone.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }
}
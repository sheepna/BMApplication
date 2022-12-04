package com.jnu.bmapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditBookActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        ImageView imageView=findViewById(R.id.book_picture);
        imageView.setImageResource(R.drawable.book_1);

        position=this.getIntent().getIntExtra("position",0);

        String title=this.getIntent().getStringExtra("title");
        EditText editTextTitle=findViewById(R.id.eeTitle);
        String author=this.getIntent().getStringExtra("author");
        EditText editTextAuthor=findViewById(R.id.eeAuthor);
        String translator=this.getIntent().getStringExtra("translator");
        EditText editTextTranslator=findViewById(R.id.eeTranslator);
        String publisher=this.getIntent().getStringExtra("publisher");
        EditText editTextPublisher=findViewById(R.id.eePublisher);
        String pubdate=this.getIntent().getStringExtra("pubdate");
        EditText editTextPubdate=findViewById(R.id.eePubDate);

        if(null!=title){ editTextTitle.setText(title);}
        if(null!=author){ editTextAuthor.setText(author);}
        if(null!=translator){ editTextTranslator.setText(translator);}
        if(null!=publisher){ editTextPublisher.setText(publisher);}
        if(null!=pubdate){ editTextPubdate.setText(pubdate);}

        Button buttonOk=findViewById(R.id.button_yes);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent() ;
                Bundle bundle =new Bundle() ;

                bundle .putString("title",editTextTitle.getText().toString());
                bundle .putString("author",editTextAuthor.getText().toString());
                bundle .putString("publisher",editTextPublisher.getText().toString());
                bundle .putString("pubdate",editTextPubdate.getText().toString());
                bundle.putString("translator",editTextTranslator.getText().toString());
                bundle.putInt("position",position );

                intent .putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                EditBookActivity.this.finish();
            }
        });

        Button buttonNo=findViewById(R.id.button_no);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBookActivity.this.finish();
            }
        });
    }
}
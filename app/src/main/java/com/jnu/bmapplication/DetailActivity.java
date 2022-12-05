package com.jnu.bmapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESSES=999;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView=findViewById(R.id.detail_picture);
        imageView.setImageResource(R.drawable.book_1);

        position=this.getIntent().getIntExtra("position",0);

        String title=this.getIntent().getStringExtra("title");
        TextView dTextTitle=findViewById(R.id.ddTitle);
        String author=this.getIntent().getStringExtra("author");
        TextView dTextAuthor=findViewById(R.id.ddAuthor);
        String translator=this.getIntent().getStringExtra("translator");
        TextView dTextTranslator=findViewById(R.id.ddTranslator);
        String publisher=this.getIntent().getStringExtra("publisher");
        TextView dTextPublisher=findViewById(R.id.ddPublisher);
        String pubdate=this.getIntent().getStringExtra("pubdate");
        TextView dTextPubdate=findViewById(R.id.ddPubDate);

        if(null!=title){ dTextTitle.setText(title);}
        if(null!=author){ dTextAuthor.setText(author);}
        if(null!=translator){ dTextTranslator.setText(translator);}
        if(null!=publisher){ dTextPublisher.setText(publisher);}
        if(null!=pubdate){ dTextPubdate.setText(pubdate);}

        Button buttonNo=findViewById(R.id.detail_button);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.finish();
            }
        });
    }
}
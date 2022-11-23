package com.jnu.bmapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.bmapplication.Data.Book;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainRecycleViewAdapter mainRecycleViewAdapter;
    private static final int MENU_ID_ADD = 1;
    private static final int MENU_ID_DELETE = 2;
    private static final int MENU_ID_UPDATE = 3;

    private ArrayList<Book>books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //准备数据
        books=new ArrayList<Book>();
        getBook();

        RecyclerView recyclerViewMain=findViewById(R.id.recycleview_main);
        //定义布局管理
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);
        //接收数据
        mainRecycleViewAdapter=new MainRecycleViewAdapter(books);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }
    //ContextMenu实现

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case MENU_ID_DELETE:
                AlertDialog alertDialog;
                alertDialog=new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                books.remove(item.getOrder());
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    //调节器代码
    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder>  {

        private ArrayList<Book> localDataSet;


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

            private final TextView textView;
            private final TextView textView1;
            private final TextView textView2;
            private final TextView textView3;
            private final ImageView imageView;
            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View
                imageView = view.findViewById(R.id.imageView);
                textView = (TextView) view.findViewById(R.id.biaoti);
                textView1=(TextView) view.findViewById(R.id.ap);
                textView2=(TextView) view.findViewById(R.id.time);
                textView3=(TextView) view.findViewById(R.id.publi);

                view.setOnCreateContextMenuListener(this);
            }

            public TextView getTextView() {
                return textView;
            }

            public TextView getTextView1() {
                return textView1;
            }

            public TextView getTextView2() {
                return textView2;
            }

            public TextView getTextView3() { return textView3;}

            public ImageView getImageView() {
                return imageView;
            }


            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_UPDATE,getAdapterPosition(),"Update ");
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"Delete ");

            }
        }


        public MainRecycleViewAdapter(ArrayList<Book> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            //LayoutInflater渲染器
            //调用该窗口的渲染器从布局文件渲染，主动创建一个view
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.book_layout, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.getTextView().setText(localDataSet.get(position).getTitle());
            viewHolder.getTextView1().setText(localDataSet.get(position).getAp());
            viewHolder.getTextView2().setText(localDataSet.get(position).getTime());
            viewHolder.getTextView3().setText(localDataSet.get(position).getPubli());
            viewHolder.getImageView().setImageResource(localDataSet.get(position).getCoverResourceId());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    public void getBook(){
        books.add(new Book("人生海海","麦家 ","北京十月文艺出版社","2019-04",R.drawable.book_1));
        books.add(new Book("兄弟","余华","作家出版社","2012-09",R.drawable.book_1));
        books.add(new Book("三体","刘慈欣","电子影音出版社","2018-12",R.drawable.book_1));
        books.add(new Book("命运","蔡崇达","广州出版社","2022-09",R.drawable.book_1));
        books.add(new Book("白鹿原","陈忠实","人民文学出版社","2010-07",R.drawable.book_1));
        books.add(new Book("我与地坛","史铁生","人民文学出版社","2011-04",R.drawable.book_1));
    }
}